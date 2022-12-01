DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `user_name` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
                            `nick_name` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
                            `user_password` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
                            `user_status` CHAR(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
                            `email` VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
                            `phonenumber` VARCHAR(32) DEFAULT NULL COMMENT '手机号',
                            `sex` CHAR(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
                            `avatar` VARCHAR(128) DEFAULT NULL COMMENT '头像',
                            `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
                            `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '更新时间',
                            `del_flag` INT(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
                           `id` BIGINT(200) NOT NULL AUTO_INCREMENT,
                           `title` VARCHAR(256) DEFAULT NULL COMMENT '标题',
                           `content` LONGTEXT COMMENT '文章内容',
                           `type` CHAR(1) DEFAULT NULL COMMENT '文章类型:1 文章, 2 草稿',
                           `summary` VARCHAR(1024) DEFAULT NULL COMMENT '文章摘要',
                           `category_id` BIGINT(20) DEFAULT NULL COMMENT '所属分类id',
                           `thumbnail` VARCHAR(256) DEFAULT NULL COMMENT '缩略图',
                           `is_top` CHAR(1) DEFAULT '0' COMMENT '是否置顶（0否，1是）',
                           `article_status` CHAR(1) DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
                           `comment_count` BIGINT(200) DEFAULT '0' COMMENT '评论数',
                           `view_count` BIGINT(200) DEFAULT '0' COMMENT '访问量',
                           `is_comment` CHAR(1) DEFAULT '1' COMMENT '是否允许评论 1是，0否',
                           `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '发布时间',
                           `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                           `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                           `del_flag` INT(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                           PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                           `type` CHAR(1) DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
                           `article_id` BIGINT(20) DEFAULT NULL COMMENT '文章id',
                           `root_id` BIGINT(20) DEFAULT '-1' COMMENT '根评论id',
                           `content` VARCHAR(512) DEFAULT NULL COMMENT '评论内容',
                           `filter_content` VARCHAR(512) DEFAULT NULL COMMENT '过滤敏感词后的评论内容',
                           `to_comment_user_id` BIGINT(20) DEFAULT '-1' COMMENT '所回复的目标评论的userid',
                           `to_comment_id` BIGINT(20) DEFAULT '-1' COMMENT '回复目标评论id',
                           `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                           `create_nick` VARCHAR(125) NULL comment '创建人昵称',
                           `avatar_url` VARCHAR(225) NULL comment '创建人头像',
                           `label` CHAR DEFAULT '0' NULL comment '标签 (0-普通评论，1-置顶评论)',
                           `comment_status` CHAR(1) DEFAULT '0' COMMENT '评论状态（0-未精选，1-精选评论）',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '发布时间',
                           `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                           `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                           `del_flag` INT(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                           PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                        `website_name` VARCHAR(256) DEFAULT NULL COMMENT '网站名',
                        `logo` VARCHAR(256) DEFAULT NULL COMMENT 'logo',
                        `website_description` VARCHAR(512) DEFAULT NULL COMMENT '描述',
                        `address` VARCHAR(128) DEFAULT NULL COMMENT '网站地址',
                        `link_status` CHAR(1) DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
                        `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '发布时间',
                        `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                        `del_flag` INT(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                        PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='友链';

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
                            `id` BIGINT(200) NOT NULL AUTO_INCREMENT,
                            `category_name` VARCHAR(128) DEFAULT NULL COMMENT '分类名',
                            `pid` BIGINT(200) DEFAULT '-1' COMMENT '父分类id，如果没有父分类为-1',
                            `category_description` VARCHAR(512) DEFAULT NULL COMMENT '描述',
                            `category_status` CHAR(1) DEFAULT '0' COMMENT '状态0:正常,1禁用',
                            `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '发布时间',
                            `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                            `del_flag` INT(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

DROP TABLE IF EXISTS `collection`;

CREATE TABLE `collection` (
                              `id` BIGINT(200) NOT NULL AUTO_INCREMENT,
                              `article_id` BIGINT(20) DEFAULT NULL COMMENT '文章id',
                              `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人的用户id',
                              `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
                              `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                              `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                              `del_flag` INT(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                              PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
                            `id` BIGINT(200) NOT NULL AUTO_INCREMENT,
                            `article_id` BIGINT(20) DEFAULT NULL COMMENT '文章id',
                            `create_by` BIGINT(20) DEFAULT NULL COMMENT '提交问题的用户id',
                            `question_description` VARCHAR(512) DEFAULT NULL COMMENT '问题描述',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '提交时间',
                            `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                            `del_flag` INT(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='反馈表';

DROP TABLE IF EXISTS `index`;

CREATE TABLE `index` (
                         `id` BIGINT(200) NOT NULL AUTO_INCREMENT,
                         `article_id` BIGINT(20) DEFAULT NULL COMMENT '文章id',
                         `index_type` CHAR(1) DEFAULT '1' COMMENT '索引类型',
                         `index_value` VARCHAR(125) DEFAULT NULL COMMENT '索引内容',
                         `index_position` VARCHAR(125) DEFAULT NULL COMMENT '索引位置',
                         `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '提交时间',
                         `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '最后编辑时间',
                         `del_flag` INT(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                         PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='索引表';

DROP TABLE IF EXISTS `sensitive_words`;

CREATE TABLE `sensitive_words` (
                                   id BIGINT(200) NOT NULL COMMENT '主键ID',
                                   word VARCHAR(30) NULL COMMENT '敏感词',
                                   PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='敏感词表';

DROP TABLE IF EXISTS `sys_message`;

CREATE TABLE `sys_message` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
                               `from_id` bigint(20) DEFAULT NULL COMMENT '触发消息用户ID（-1：系统消息）',
                               `from_username` varchar(256) DEFAULT NULL COMMENT '触发消息用户昵称',
                               `avatar_url` varchar(512) DEFAULT NULL COMMENT '触发消息用户头像',
                               `to_id` bigint(20) DEFAULT NULL COMMENT '目标用户ID',
                               `conversation_type` varchar(225) DEFAULT NULL COMMENT '消息类型',
                               `content` varchar(512) DEFAULT NULL COMMENT '消息内容',
                               `status` char(255) DEFAULT '0' COMMENT '消息状态（''0''-未读，''1''-已读）',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `del_flag` int(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';