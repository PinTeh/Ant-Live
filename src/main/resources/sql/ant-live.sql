/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : ant-live

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 17/10/2021 10:24:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `positive_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reverse_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NOT NULL,
  `hand_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `operator` int(11) NULL DEFAULT NULL,
  `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ban_record
-- ----------------------------
DROP TABLE IF EXISTS `ban_record`;
CREATE TABLE `ban_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NULL DEFAULT NULL,
  `resume_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bill_change` decimal(10, 2) NOT NULL,
  `type` int(11) NOT NULL,
  `balance` decimal(10, 2) NOT NULL,
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_BILL_USER_ID`(`user_id`) USING BTREE,
  CONSTRAINT `FK_BILL_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `disabled` int(11) NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_detect
-- ----------------------------
DROP TABLE IF EXISTS `live_detect`;
CREATE TABLE `live_detect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `confidence` int(11) NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `normal_score` int(11) NULL DEFAULT NULL,
  `hot_score` int(11) NULL DEFAULT NULL,
  `porn_score` int(11) NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `polity_score` int(11) NULL DEFAULT NULL,
  `illegal_score` int(11) NULL DEFAULT NULL,
  `terror_score` int(11) NULL DEFAULT NULL,
  `handle_status` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `screenshot_time` int(11) NULL DEFAULT NULL,
  `resume_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_info
-- ----------------------------
DROP TABLE IF EXISTS `live_info`;
CREATE TABLE `live_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `room_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `click_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dan_mu_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `present_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_ROOM_INFO_ROOM_ID`(`room_id`) USING BTREE,
  CONSTRAINT `FK_ROOM_INFO_ROOM_ID` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_index` int(11) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NULL DEFAULT 0,
  `sort` int(11) NULL DEFAULT NULL,
  `hidden` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for present
-- ----------------------------
DROP TABLE IF EXISTS `present`;
CREATE TABLE `present`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT 0,
  `disabled` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for present_reward
-- ----------------------------
DROP TABLE IF EXISTS `present_reward`;
CREATE TABLE `present_reward`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_id` int(11) NULL DEFAULT NULL,
  `to_id` int(11) NULL DEFAULT NULL,
  `room_id` int(11) NULL DEFAULT NULL,
  `video_id` int(11) NULL DEFAULT NULL,
  `present_id` int(11) NULL DEFAULT NULL,
  `number` int(11) NULL DEFAULT NULL,
  `unit_price` decimal(10, 2) NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '0-为直播间礼物 1-为视频打赏礼物',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_ROOM_PRESENT_ROOM_ID`(`room_id`) USING BTREE,
  CONSTRAINT `FK_ROOM_PRESENT_ROOM_ID` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `FK_ROLE_MENU_MENU_ID`(`menu_id`) USING BTREE,
  CONSTRAINT `FK_ROLE_MENU_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_ROLE_MENU_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `rtmp_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `disabled` int(11) NULL DEFAULT 0,
  `status` int(11) NULL DEFAULT -1,
  `category_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_ROOM_CATEGORY_ID`(`category_id`) USING BTREE,
  CONSTRAINT `FK_ROOM_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for statistic_speak
-- ----------------------------
DROP TABLE IF EXISTS `statistic_speak`;
CREATE TABLE `statistic_speak`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `number` int(11) NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for statistic_view
-- ----------------------------
DROP TABLE IF EXISTS `statistic_view`;
CREATE TABLE `statistic_view`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `member_number` int(11) NULL DEFAULT NULL,
  `visitor_number` int(11) NULL DEFAULT NULL COMMENT '游客浏览数',
  `total_number` int(11) NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_push
-- ----------------------------
DROP TABLE IF EXISTS `sys_push`;
CREATE TABLE `sys_push`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `open` int(11) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `listener_items` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_SYSTEM_PUSH_USER_ID`(`user_id`) USING BTREE,
  CONSTRAINT `FK_SYSTEM_PUSH_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_push_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_push_log`;
CREATE TABLE `sys_push_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `sys_push_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/gray_avatar.jpg',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_validated` int(11) NULL DEFAULT 0,
  `disabled` int(11) NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 100,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10019 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FK_USER_ROLE_ROLE_ID`(`role_id`) USING BTREE,
  CONSTRAINT `FK_USER_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_USER_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `video_category_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for watch
-- ----------------------------
DROP TABLE IF EXISTS `watch`;
CREATE TABLE `watch`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `room_id` int(11) NULL DEFAULT NULL,
  `watch_type` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_uid_rid_wt`(`user_id`, `room_id`, `watch_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for withdrawal
-- ----------------------------
DROP TABLE IF EXISTS `withdrawal`;
CREATE TABLE `withdrawal`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identity` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款账号',
  `identity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提现用户名',
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'alipay - wechat',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `status` int(11) NULL DEFAULT 0 COMMENT '当前状态 0-未完成 1-完成',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `virtual_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '提现金豆',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '提现金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `ant-live`.`present`(`id`, `name`, `icon`, `price`, `create_time`, `update_time`, `sort`, `disabled`) VALUES (1, '火箭', 'http://image.imhtb.cn/飞机.png', 10.00, '2020-02-26 18:20:48', '2020-05-25 22:34:46', 0, 0);
INSERT INTO `ant-live`.`present`(`id`, `name`, `icon`, `price`, `create_time`, `update_time`, `sort`, `disabled`) VALUES (2, '飞机', 'http://image.imhtb.cn/飞机1.png', 88000.00, '2020-03-02 10:01:35', '2020-05-25 22:34:39', 3, 0);

INSERT INTO `ant-live`.`role`(`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (1, '超级管理员', 1, '2020-04-30 13:57:24', '2020-04-30 13:57:27', 'ROLE_ROOT');
INSERT INTO `ant-live`.`role`(`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (2, '直播管理员', 2, '2020-04-30 13:57:46', '2020-04-30 13:57:48', 'ROLE_LIVE');
INSERT INTO `ant-live`.`role`(`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (100, '普通会员', 8, '2020-04-30 15:16:56', '2020-04-30 15:16:58', 'ROLE_COMMON');
INSERT INTO `ant-live`.`role`(`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (101, '身份认证管理员', 2, '2020-05-09 11:21:03', NULL, 'ROLE_AUTH');

INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (1, 1, 'el-icon-data-board', 'dashboard', '首页', 0, 1, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (2, 2, 'el-icon-user', 'user-manage', '会员中心', 0, 2, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (3, 3, 'el-icon-coordinate', 'user-auth', '身份验证', 0, 3, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (4, 4, 'el-icon-bangzhu', 'live-room-manage', '直播管理', 16, 4, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (5, 5, 'el-icon-data-analysis', 'live-info-manage', '直播数据', 16, 5, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (6, 6, 'el-icon-menu', 'system-settings', '系统设置', 0, 6, 1, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (7, 7, 'el-icon-data-analysis', 'data-analysis', '数据统计', 0, 90, 0, NULL, '2020-05-23 19:41:03');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (8, 8, 'el-icon-goods', 'present-manage', '礼物配置', 0, 8, 0, NULL, '2020-05-27 11:18:51');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (9, 9, 'el-icon-s-shop', 'live-ban-manage', '小黑屋', 16, 9, 1, NULL, '2020-05-23 19:48:26');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (10, 10, 'el-icon-chat-line-round', 'message-push', '消息推送', 0, 88, 0, NULL, '2020-05-09 11:25:27');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (11, 11, 'el-icon-data-analysis', 'system-monitor-host', '服务监控', 12, 11, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (12, 12, 'el-icon-warning-outline', 'system-monitor', '系统监控', 0, 18, 0, NULL, '2020-05-27 11:18:18');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (13, 13, 'el-icon-data-analysis', 'system-manage', '系统管理', 0, 13, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (14, 14, 'el-icon-data-analysis', 'system-manage-menu', '菜单管理', 13, 14, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (15, 15, 'el-icon-data-analysis', 'system-manage-role', '角色管理', 13, 15, 0, NULL, NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (16, 16, 'el-icon-video-camera', 'live-center', '直播中心', 0, 16, 0, NULL, '2020-05-09 11:25:52');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (17, 17, 'el-icon-data-board', 'live-detect', '截图检测', 16, 6, 0, '2020-05-08 14:36:26', '2020-05-23 19:47:54');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (18, 18, 'el-icon-data-analysis', 'bill', '账单中心', 0, 4, 0, '2020-05-09 11:11:18', NULL);
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (19, 19, 'el-icon-user', 'user-role-manage', '角色分配', 13, 1, 0, '2020-05-09 17:24:39', '2020-05-19 11:39:04');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (20, 20, 'el-icon-data-board', 'snapshot-templates', '鉴黄模板', 16, 1, 0, '2020-05-22 14:32:58', '2020-05-23 19:15:10');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (21, 21, 'el-icon-warning-outline', 'ban-record', '封禁记录', 16, 1, 0, '2020-05-23 18:27:47', '2020-05-23 19:15:05');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (22, NULL, 'el-icon-data-analysis', 'vod', '视频中心', 0, 17, 0, '2020-05-25 16:13:02', '2020-05-27 11:17:53');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (23, NULL, 'el-icon-data-analysis', 'video-manage', '视频管理', 22, 1, 0, '2020-05-25 16:13:51', '2020-05-27 10:25:21');
INSERT INTO `ant-live`.`menu`(`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (24, NULL, 'el-icon-data-analysis', 'vod-list', '稿件列表', 22, 2, 0, '2020-05-27 11:51:19', NULL);

INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 1);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (101, 1);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (101, 3);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 4);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 5);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 5);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 7);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 8);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 9);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 9);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 10);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 11);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 12);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 13);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 14);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 15);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 16);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 16);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 17);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 17);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 18);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 19);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 20);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 20);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 21);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (2, 21);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 22);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 23);
INSERT INTO `ant-live`.`role_menu`(`role_id`, `menu_id`) VALUES (1, 24);

INSERT INTO `ant-live`.`user`(`id`, `password`, `mobile`, `avatar`, `signature`, `birthday`, `sex`, `nick_name`, `create_time`, `update_time`, `email`, `is_validated`, `disabled`, `role_id`) VALUES (10001, '$2a$10$puULYxVheVu/sJZk7rUbvujNheV9v7afPWETHv47sjS2KAXNptTEe', '', 'http://image.imhtb.cn/avatar.png', '个性签名', NULL, '男', 'admin', '2020-05-09 18:47:23', '2020-05-09 11:33:16', '794409767@qq.com', 0, 0, 1);

INSERT INTO `ant-live`.`user_role`(`user_id`, `role_id`, `create_time`, `update_time`) VALUES (10001, 1, NULL, NULL);
INSERT INTO `ant-live`.`user_role`(`user_id`, `role_id`, `create_time`, `update_time`) VALUES (10002, 2, NULL, NULL);

INSERT INTO `ant-live`.`room`(`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (1, '', 'Spring Security', 'https://images.unsplash.com/photo-1582917205301-bbb4afb5501f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80', '简介', '通知公告', 10001, 'http://www.bing.cn/live/', 0, 0, 1, '2020-03-04 06:40:32', '2020-05-22 13:37:29', 'xxx');
