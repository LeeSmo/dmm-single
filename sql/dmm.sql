/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50646
Source Host           : localhost:3306
Source Database       : dmm

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2021-07-16 17:33:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_common_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_file`;
CREATE TABLE `sys_common_file` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `file_size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `create_id` bigint(50) DEFAULT NULL COMMENT '创建人id',
  `create_name` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统文件表';

-- ----------------------------
-- Records of sys_common_file
-- ----------------------------
INSERT INTO `sys_common_file` VALUES ('88686', '你好', '/2021/07/09/', '.doc', '33', null, null, '2021-07-14 19:31:37', null, 'n');
INSERT INTO `sys_common_file` VALUES ('-7071415632718303815', 'fff.jpg', '/FILE/20210716/1626426067989.jpg', '.jpg', '37929', '10001', '超管', '2021-07-16 17:01:02', null, 'n');
INSERT INTO `sys_common_file` VALUES ('-5862102665174064189', '付费网站.txt', '/FILE/20210716/1626426906598.txt', '.txt', '262', '10001', '超管', '2021-07-16 17:15:05', null, 'n');
INSERT INTO `sys_common_file` VALUES ('-8888245667292818378', 'lyt.jpg', '/FILE/20210716/1626426915734.jpg', '.jpg', '105170', '10001', '超管', '2021-07-16 17:15:10', null, 'n');
INSERT INTO `sys_common_file` VALUES ('-8019558763697808299', 'banner.jpg', '/FILE/20210716/1626426919670.jpg', '.jpg', '105170', '10001', '超管', '2021-07-16 17:15:14', null, 'n');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pattern` varchar(255) DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='系统菜单资源表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('101', '/admin/**');
INSERT INTO `sys_menu` VALUES ('102', '/user/**');

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(50) DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='资源角色关系表';

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES ('1', '101', '1');
INSERT INTO `sys_menu_role` VALUES ('2', '102', '3');

-- ----------------------------
-- Table structure for sys_quartz
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz`;
CREATE TABLE `sys_quartz` (
  `ID` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务表';

-- ----------------------------
-- Records of sys_quartz
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(50) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(100) DEFAULT NULL COMMENT '角色编码',
  `delete_flag` varchar(2) DEFAULT NULL COMMENT '删除标记',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `show_state` varchar(2) DEFAULT NULL COMMENT '隐藏状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER_ADMIN', 'n', null, null, null, null);
INSERT INTO `sys_role` VALUES ('2', '系统管理员', 'ROLE_ADMIN', 'n', null, null, null, null);
INSERT INTO `sys_role` VALUES ('3', '普通用户', 'ROLE_USER', 'n', null, null, null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(50) NOT NULL COMMENT 'ID',
  `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(100) DEFAULT '' COMMENT '真实姓名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(100) DEFAULT '' COMMENT '密码盐',
  `phone_number` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `wechat_id` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信号',
  `qq` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QQ',
  `card_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号码',
  `role_ids` varchar(500) DEFAULT '' COMMENT '角色列表',
  `depart_id` varchar(32) DEFAULT '' COMMENT '部门ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` int(11) DEFAULT '0' COMMENT '状态',
  `data_flag` int(11) DEFAULT '0' COMMENT '0正常,1隐藏',
  `delete_flag` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('10001', 'admin', '超管', '$2a$10$b.q6khiW8qUXOYZM5pp6AuXpOOzPUHsZVjIWlTRNBqBWboeEEx7CG', '123456', null, null, null, null, null, '3', null, '2020-12-24 10:45:25', null, null, null, null);
INSERT INTO `sys_user` VALUES ('10002', 'person', '张三', '123', '123', null, null, null, null, null, 'student', '1318103339229106178', '2020-04-20 14:41:35', '2020-04-20 14:41:35', '0', '0', null);
INSERT INTO `sys_user` VALUES ('10003', 'user', '用户1', '$2a$10$GqVTKSpiep3Tyt.kOd7a4.SAeegfOBux11D13pjXQKnKH8GV0K74u', null, null, null, null, null, null, '3', null, '2020-12-24 10:47:38', null, null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(50) NOT NULL,
  `user_id` bigint(50) DEFAULT NULL,
  `role_id` bigint(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '10001', '1');
INSERT INTO `sys_user_role` VALUES ('2', '10002', '3');
INSERT INTO `sys_user_role` VALUES ('3', '10003', '3');

-- ----------------------------
-- Table structure for sys_zip
-- ----------------------------
DROP TABLE IF EXISTS `sys_zip`;
CREATE TABLE `sys_zip` (
  `id` bigint(50) NOT NULL,
  `file_id` bigint(50) DEFAULT NULL COMMENT '文件id',
  `zip_name` varchar(100) DEFAULT NULL COMMENT '压缩包名',
  `zip_url` varchar(100) DEFAULT NULL COMMENT '压缩包路径',
  `zip_key` varchar(50) DEFAULT NULL COMMENT '解压密码',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件压缩对照表';

-- ----------------------------
-- Records of sys_zip
-- ----------------------------
