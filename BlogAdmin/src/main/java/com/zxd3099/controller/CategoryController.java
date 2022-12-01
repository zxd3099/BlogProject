package com.zxd3099.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.CategoryDto;
import com.zxd3099.domain.entity.Category;
import com.zxd3099.domain.vo.CategoryVo;
import com.zxd3099.domain.vo.ExcelCategoryVo;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.service.CategoryService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zxd3099
 * @create 2022-11-28-19:18
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNumber, Integer pageSize, String name, String status){
        return categoryService.listCategory(pageNumber, pageSize, name, status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);

            // 将数据写入Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                     .autoCloseStream(Boolean.FALSE)
                     .sheet("分类导出")
                     .doWrite(excelCategoryVos);
        } catch (Exception e) {
            // 如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
