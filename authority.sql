/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : authority

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 20/03/2020 09:31:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `css` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `href` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` tinyint(255) DEFAULT NULL,
  `permission` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '用户管理', 'fa-users', NULL, 1, NULL, 1);
INSERT INTO `sys_permission` VALUES (2, 1, '用户查询', 'fa-user', '/api/getPage?pageName=user/user-list', 1, NULL, 2);
INSERT INTO `sys_permission` VALUES (3, 2, '查询', NULL, NULL, 2, 'sys:user:query', 100);
INSERT INTO `sys_permission` VALUES (4, 2, '新增', NULL, NULL, 2, 'sys:user:add', 100);
INSERT INTO `sys_permission` VALUES (5, 2, '删除', NULL, NULL, 2, 'sys:user:del', 100);
INSERT INTO `sys_permission` VALUES (6, 1, '修改密码', 'fa-pencil-square-o', '/api/getPage?pageName=user/user-chanage-password', 1, 'sys:user:password', 4);
INSERT INTO `sys_permission` VALUES (7, 0, '系统设置', 'fa-gears', NULL, 1, NULL, 5);
INSERT INTO `sys_permission` VALUES (8, 7, '权限管理', 'fa-permission', '/api/getPage?pageName=permission/permission-list', 1, NULL, 6);
INSERT INTO `sys_permission` VALUES (9, 8, '查询', NULL, NULL, 2, 'sys:permission:query', 100);
INSERT INTO `sys_permission` VALUES (10, 8, '新增', NULL, NULL, 2, 'sys:permission:add', 100);
INSERT INTO `sys_permission` VALUES (11, 8, '删除', NULL, NULL, 2, 'sys:permission:del', 100);
INSERT INTO `sys_permission` VALUES (12, 7, '角色', 'fa-user-secret', '/api/getPage?pageName=role/role-list', 1, NULL, 7);
INSERT INTO `sys_permission` VALUES (13, 12, '查询', NULL, NULL, 2, 'sys:role:query', 100);
INSERT INTO `sys_permission` VALUES (14, 12, '新增', NULL, NULL, 2, 'sys:role:add', 100);
INSERT INTO `sys_permission` VALUES (15, 12, '删除', NULL, NULL, 2, 'sys:role:del', 100);
INSERT INTO `sys_permission` VALUES (16, 0, '文件管理', 'fa-folder-open', '/api/getPage?pageName', 1, NULL, 8);
INSERT INTO `sys_permission` VALUES (17, 16, '查询', NULL, NULL, 2, 'sys:file:query', 100);
INSERT INTO `sys_permission` VALUES (18, 16, '删除', NULL, NULL, 2, 'sys:file:del', 100);
INSERT INTO `sys_permission` VALUES (19, 0, '数据源监控', 'fa-eye', 'druid/index.html', 1, NULL, 9);
INSERT INTO `sys_permission` VALUES (20, 0, '接口swagger', 'fa-file-pdf-o', 'swagger-ui.html', 1, NULL, 10);
INSERT INTO `sys_permission` VALUES (21, 0, '代码生成', 'fa-wrench', '/api/getPage?pageName', 1, 'generate:edit', 11);
INSERT INTO `sys_permission` VALUES (22, 0, '日志查询', 'fa-reorder', '/api/getPage?pageName', 1, 'sys:log:query', 13);
INSERT INTO `sys_permission` VALUES (23, 8, '修改', NULL, NULL, 2, 'sys:permission:edit', 100);
INSERT INTO `sys_permission` VALUES (24, 12, '修改', NULL, NULL, 2, 'sys:role:edit', 100);
INSERT INTO `sys_permission` VALUES (25, 2, '修改', NULL, NULL, 2, 'sys:user:edit', 100);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `createTime` datetime(0) DEFAULT NULL,
  `updateTime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', '2020-03-13 14:17:56', '2020-03-19 15:25:15');
INSERT INTO `sys_role` VALUES (2, 'USER', '普通用户', '2020-03-13 14:18:40', '2020-03-13 14:18:42');
INSERT INTO `sys_role` VALUES (3, 'TEACHER', '教师', '2020-03-13 14:18:44', '2020-03-13 14:18:46');
INSERT INTO `sys_role` VALUES (4, 'TEST', 'TEST', '2020-03-13 14:18:48', '2020-03-13 14:18:49');
INSERT INTO `sys_role` VALUES (19, 'ff', 'dd', '2020-03-17 18:12:42', '2020-03-18 08:10:46');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `permissionId`) USING BTREE,
  INDEX `fk_sysrolepermission_permissionId`(`permissionId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (1, 10);
INSERT INTO `sys_role_permission` VALUES (1, 11);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 13);
INSERT INTO `sys_role_permission` VALUES (1, 14);
INSERT INTO `sys_role_permission` VALUES (1, 15);
INSERT INTO `sys_role_permission` VALUES (1, 16);
INSERT INTO `sys_role_permission` VALUES (1, 17);
INSERT INTO `sys_role_permission` VALUES (1, 18);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (1, 20);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (1, 22);
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (1, 24);
INSERT INTO `sys_role_permission` VALUES (1, 25);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`, `roleId`) USING BTREE,
  INDEX `fk_roleId`(`roleId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (12, 2);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `headImgUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `telephone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `createTime` datetime(0) DEFAULT NULL,
  `updateTime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'yangang', '$2a$10$5UeJZhlB7D1/jm08iQ7.2uAJA7rir8J.eUm7cdVzJoVPLYU9GhDRG', '张三', '1', '1', '66666666', NULL, '2020-03-13', NULL, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (5, 'zhangsan', '$2a$10$5UeJZhlB7D1/jm08iQ7.2uAJA7rir8J.eUm7cdVzJoVPLYU9GhDRG', '李四', '5', '5', '66666666', NULL, '2020-03-11', NULL, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (7, 'lisi', '222222', '李四', '7', '7', '66666666', NULL, '2020-03-11', NULL, 2, NULL, NULL);
INSERT INTO `sys_user` VALUES (9, 'lisi', '222222', '李四', '9', '9', '66666666', NULL, '2020-03-11', NULL, 2, NULL, NULL);
INSERT INTO `sys_user` VALUES (10, 'lisi', '222222', '李四', '10', '10', '6666222', '123@qq.com', '2020-03-14', NULL, 1, NULL, '2020-03-14 09:56:05');
INSERT INTO `sys_user` VALUES (11, 'lisi', '222222', '李四', '11', '11', '66666666', NULL, '2020-03-11', NULL, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (12, 'yangang2', 'e10adc3949ba59abbe56e057f20f883e', '123', NULL, NULL, '12345687', '123@qq.com', '2020-03-11', NULL, 1, '2020-03-13 16:54:55', '2020-03-13 16:54:55');

SET FOREIGN_KEY_CHECKS = 1;
