package com.zxd3099.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ll
 * @create 2022-11-30-15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private String description;
    String status;
}
