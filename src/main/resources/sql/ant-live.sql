/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ant-live-dev

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 08/05/2022 11:20:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `positive_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `reverse_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `status` int NOT NULL,
                        `hand_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `operator` int DEFAULT NULL,
                        `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of auth
-- ----------------------------
BEGIN;
INSERT INTO `auth` (`id`, `user_id`, `real_name`, `positive_url`, `reverse_url`, `card_no`, `status`, `hand_url`, `create_time`, `update_time`, `operator`, `reject_reason`) VALUES (9, 10001, '番茄蛋', 'http://image.imhtb.cn/avatar.png', 'http://image.imhtb.cn/avatar.png', '777888333378777727', 1, 'http://image.imhtb.cn/avatar.png', '2020-05-20 15:50:13', '2020-05-22 13:37:29', 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ban_record
-- ----------------------------
DROP TABLE IF EXISTS `ban_record`;
CREATE TABLE `ban_record` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `room_id` int DEFAULT NULL,
                              `resume_time` datetime DEFAULT NULL,
                              `create_time` datetime DEFAULT NULL,
                              `update_time` datetime DEFAULT NULL,
                              `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                              `start_time` datetime DEFAULT NULL,
                              `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                              `status` int DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ban_record
-- ----------------------------
BEGIN;
INSERT INTO `ban_record` (`id`, `room_id`, `resume_time`, `create_time`, `update_time`, `reason`, `start_time`, `mark`, `status`) VALUES (1, 1001, '2020-05-23 18:46:15', '2020-05-23 18:46:17', '2020-05-23 18:46:19', '封禁原因', '2020-05-23 18:46:26', '备注', 0);
INSERT INTO `ban_record` (`id`, `room_id`, `resume_time`, `create_time`, `update_time`, `reason`, `start_time`, `mark`, `status`) VALUES (2, 16, '2020-05-27 16:00:00', '2020-05-27 20:54:14', NULL, '涉黄', '2020-05-27 20:54:14', '手动恢复', 1);
COMMIT;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `bill_change` decimal(10,2) NOT NULL,
                        `type` int NOT NULL,
                        `balance` decimal(10,2) NOT NULL,
                        `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `FK_BILL_USER_ID` (`user_id`) USING BTREE,
                        CONSTRAINT `FK_BILL_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bill
-- ----------------------------
BEGIN;
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (1, 10001, 200.00, 0, 200.00, '1', NULL, '2020-03-16 11:15:50', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (2, 10001, -2.00, 1, 198.00, '1', NULL, '2020-03-11 11:15:52', NULL, '323934545648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (3, 10002, 0.00, 0, 0.00, '1', NULL, '2020-03-25 01:52:17', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (6, 10001, 88.88, 1, 109.12, NULL, '向直播间2赠送礼物', '2020-03-25 01:54:15', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (7, 10002, 88.88, 0, 88.88, NULL, '收获礼物', '2020-03-25 01:54:15', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (8, 10001, 99.99, 1, 9.13, NULL, '向直播间2赠送礼物', '2020-03-25 01:54:23', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (9, 10002, 99.99, 0, 188.87, NULL, '收获礼物', '2020-03-25 01:54:23', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (10, 10001, 100000.00, 0, 100000.00, NULL, NULL, '2020-04-06 19:54:09', NULL, '587946745648');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (11, 10001, 88000.00, 1, 12000.00, NULL, '赠送礼物', '2020-04-06 19:54:37', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (12, 10002, 88000.00, 0, 88188.87, NULL, '收获礼物', '2020-04-06 19:54:37', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (13, 10001, 1000.00, 1, 11000.00, NULL, '赠送礼物', '2020-04-06 19:55:04', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (14, 10002, 1000.00, 0, 89188.87, NULL, '收获礼物', '2020-04-06 19:55:04', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (15, 10001, -100.00, 1, 10900.00, NULL, '提现', '2020-04-08 18:34:42', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (16, 10001, -1000.00, 1, 9900.00, NULL, '提现', '2020-04-08 19:12:52', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (17, 10001, -1000.00, 1, 8900.00, NULL, '提现', '2020-04-08 19:13:36', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (18, 10001, -1000.00, 1, 7900.00, NULL, '提现', '2020-04-08 19:15:08', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (19, 10001, -1000.00, 1, 6900.00, NULL, '提现', '2020-04-08 19:16:49', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (20, 10001, -1000.00, 1, 5900.00, NULL, '提现', '2020-04-08 19:27:05', NULL, '123118471236123');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (21, 10001, -1000.00, 1, 4900.00, NULL, '提现', '2020-04-08 19:30:10', NULL, '1a40b7bceb8270e7d96a94');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (22, 10001, -1000.00, 1, 3900.00, NULL, '提现', '2020-04-08 19:31:02', NULL, '5742b0ae83672c63b992ab');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (23, 10001, -1000.00, 1, 2900.00, NULL, '提现', '2020-04-08 19:31:19', NULL, '274a41bc9d4adf6aa4188f');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (24, 10001, -1000.00, 1, 1900.00, NULL, '提现', '2020-04-08 19:31:25', NULL, '2540099e4277d072e2a816');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (25, 10001, -1000.00, 1, 900.00, NULL, '提现', '2020-04-08 19:31:28', NULL, '044cdabc0de3367d6675f3');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (26, 10001, -100.00, 1, 800.00, NULL, '提现', '2020-04-09 23:20:24', NULL, '9644fd801615909ecccb0c');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (27, 10001, -100.00, 1, 700.00, NULL, '提现', '2020-04-09 23:30:46', NULL, '014dada040cef8aebe4292');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (28, 10001, -100.00, 1, 600.00, NULL, '提现', '2020-04-09 23:39:13', NULL, '414c7b937cc84bde6042a2');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (29, 10001, -100.00, 1, 500.00, NULL, '提现', '2020-04-10 00:12:12', NULL, '7d4be2a2e4aacdaed5c331');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (30, 10001, -100.00, 1, 400.00, NULL, '提现', '2020-04-10 00:15:09', NULL, 'e647c0947baa3f69cb017e');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (31, 10001, -100.00, 1, 300.00, NULL, '提现', '2020-04-10 00:20:50', NULL, '694e7b96bd2528308c8f18');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (32, 10001, -10.00, 1, 290.00, NULL, '提现', '2020-04-11 12:13:43', NULL, '944608a5d6cc68aeefddf1');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (35, 10001, -10.00, 1, 280.00, NULL, '赠送礼物', '2020-04-11 13:18:32', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (36, 10001, 10.00, 0, 300.00, NULL, '收获礼物', '2020-04-11 13:18:32', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (37, 10001, -10.00, 1, 290.00, NULL, '赠送礼物', '2020-04-11 13:19:11', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (38, 10001, 10.00, 0, 310.00, NULL, '收获礼物', '2020-04-11 13:19:11', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (39, 10001, -10.00, 1, 300.00, NULL, '赠送礼物', '2020-04-11 13:19:30', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (40, 10001, 10.00, 0, 320.00, NULL, '收获礼物', '2020-04-11 13:19:30', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (41, 10001, -10.00, 1, 310.00, NULL, '赠送礼物', '2020-04-11 13:20:24', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (42, 10001, 10.00, 0, 330.00, NULL, '收获礼物', '2020-04-11 13:20:24', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (43, 10001, -10.00, 1, 320.00, NULL, '赠送礼物', '2020-04-11 13:20:31', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (44, 10001, 10.00, 0, 340.00, NULL, '收获礼物', '2020-04-11 13:20:31', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (45, 10001, -10.00, 1, 330.00, NULL, '赠送礼物', '2020-04-11 13:23:04', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (46, 10001, 10.00, 0, 350.00, NULL, '收获礼物', '2020-04-11 13:23:04', NULL, NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (47, 10007, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-09 11:58:29', '2020-05-09 11:58:29', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (48, 10001, 640.00, 0, 990.00, NULL, NULL, '2020-05-11 23:53:30', '2020-05-11 23:53:30', '724847b0459cbc32954b0b');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (49, 10001, -90.00, 1, 900.00, NULL, '提现', '2020-05-11 23:54:40', '2020-05-11 23:54:40', '564032b50b2b30149a46d7');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (50, 10001, -100.00, 1, 800.00, NULL, '提现', '2020-05-12 00:01:40', '2020-05-12 00:01:40', '61459ea8206ac4aef665f1');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (51, 10001, 640.00, 0, 1440.00, NULL, NULL, '2020-05-12 00:23:28', '2020-05-12 00:23:28', '0843b2bfd8cf1d10b113a7');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (52, 10001, -440.00, 1, 1000.00, NULL, '提现', '2020-05-12 00:24:03', '2020-05-12 00:24:03', '664649aab9ba0c5e35a7f1');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (53, 10008, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-13 17:34:19', '2020-05-13 17:34:19', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (54, 10009, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-18 12:52:48', '2020-05-18 12:52:48', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (55, 10001, 640.00, 0, 1640.00, NULL, NULL, '2020-05-20 16:06:28', '2020-05-20 16:06:28', '0f4a908469d9b1807b5084');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (56, 10001, -300.00, 1, 1340.00, NULL, '提现', '2020-05-20 16:07:35', '2020-05-20 16:07:35', 'ef4b5092e41722a0da8805');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (63, 10001, -10.00, 1, 1330.00, NULL, '赠送礼物', '2020-05-25 22:47:13', '2020-05-25 22:47:13', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (64, 10002, 10.00, 0, 89198.87, NULL, '收获礼物', '2020-05-25 22:47:13', '2020-05-25 22:47:13', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (65, 10002, -1000.00, 0, 88198.87, NULL, '视频打赏', '2020-05-26 11:26:58', '2020-05-26 11:26:58', 'a14142a0602b08ca2613e5');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (66, 10001, 1000.00, 0, 2330.00, NULL, '视频打赏', '2020-05-26 11:26:58', '2020-05-26 11:26:58', '734696a97e485dde8999df');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (67, 10001, -100.00, 0, 2230.00, NULL, '直播打赏', '2020-05-26 11:29:53', '2020-05-26 11:29:53', '8f410f8f91f7df66297dc6');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (68, 10002, 100.00, 0, 88298.87, NULL, '直播打赏', '2020-05-26 11:29:53', '2020-05-26 11:29:53', '5348a6b0618dc676c07d5d');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (69, 10001, -100.00, 0, 2130.00, NULL, '直播打赏', '2020-05-26 11:30:09', '2020-05-26 11:30:09', '304b5ba33afa93c44b8d20');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (70, 10002, 100.00, 0, 88398.87, NULL, '直播打赏', '2020-05-26 11:30:09', '2020-05-26 11:30:09', '274d5cb27bbb251f941688');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (71, 10001, -1000.00, 0, 1130.00, NULL, '直播打赏', '2020-05-26 11:30:43', '2020-05-26 11:30:43', 'b64681b2df4c2dc93737e8');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (72, 10002, 1000.00, 0, 89398.87, NULL, '直播打赏', '2020-05-26 11:30:43', '2020-05-26 11:30:43', '2142ae8f0a9ed430c1d8d2');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (73, 10010, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 16:58:14', '2020-05-27 16:58:14', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (74, 10011, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:01:37', '2020-05-27 17:01:37', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (75, 10012, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:03:04', '2020-05-27 17:03:04', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (76, 10013, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:04:30', '2020-05-27 17:04:30', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (77, 10014, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:06:09', '2020-05-27 17:06:09', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (78, 10015, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:08:06', '2020-05-27 17:08:06', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (79, 10016, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:10:31', '2020-05-27 17:10:31', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (80, 10017, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:14:41', '2020-05-27 17:14:41', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (81, 10018, 0.00, 0, 0.00, NULL, '初始化账单', '2020-05-27 17:57:45', '2020-05-27 17:57:45', NULL);
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (82, 10001, -10.00, 1, 1120.00, NULL, '视频打赏', '2021-11-23 23:29:42', '2021-11-23 23:29:42', 'e5452c963e31822fb6fd0e');
INSERT INTO `bill` (`id`, `user_id`, `bill_change`, `type`, `balance`, `ip`, `mark`, `create_time`, `update_time`, `order_no`) VALUES (83, 10001, 10.00, 0, 1140.00, NULL, '视频打赏', '2021-11-23 23:29:42', '2021-11-23 23:29:42', '204d4c9fd63d4cb0ac52cf');
COMMIT;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `sort` int DEFAULT NULL,
                            `disabled` int DEFAULT NULL,
                            `is_deleted` int Not NULL DEFAULT 0,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `parent_id` int DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` (`id`, `name`, `sort`, `disabled`, `is_deleted`, `create_time`, `update_time`, `parent_id`) VALUES (1, '游戏直播', 1, 0, 0, '2020-04-19 01:33:15', '2020-04-19 01:33:17', NULL);
INSERT INTO `category` (`id`, `name`, `sort`, `disabled`, `is_deleted`, `create_time`, `update_time`, `parent_id`) VALUES (2, '娱乐直播', 1, 0, 0, '2020-04-19 01:33:35', '2020-04-19 01:33:37', NULL);
COMMIT;

-- ----------------------------
-- Table structure for live_detect
-- ----------------------------
DROP TABLE IF EXISTS `live_detect`;
CREATE TABLE `live_detect` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `room_id` int DEFAULT NULL,
                               `type` int DEFAULT NULL,
                               `confidence` int DEFAULT NULL,
                               `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                               `normal_score` int DEFAULT NULL,
                               `hot_score` int DEFAULT NULL,
                               `porn_score` int DEFAULT NULL,
                               `level` int DEFAULT NULL,
                               `polity_score` int DEFAULT NULL,
                               `illegal_score` int DEFAULT NULL,
                               `terror_score` int DEFAULT NULL,
                               `handle_status` int DEFAULT NULL,
                               `create_time` datetime DEFAULT NULL,
                               `update_time` datetime DEFAULT NULL,
                               `screenshot_time` int DEFAULT NULL,
                               `resume_time` datetime DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of live_detect
-- ----------------------------
BEGIN;
INSERT INTO `live_detect` (`id`, `room_id`, `type`, `confidence`, `img`, `normal_score`, `hot_score`, `porn_score`, `level`, `polity_score`, `illegal_score`, `terror_score`, `handle_status`, `create_time`, `update_time`, `screenshot_time`, `resume_time`) VALUES (1, 1, 1, 99, 'http://image.imhtb.cn/avatar.png', 1, 0, 99, 0, 0, 0, 0, 1, '2020-05-13 18:17:26', NULL, 1589365045, '2020-05-14 02:17:26');
INSERT INTO `live_detect` (`id`, `room_id`, `type`, `confidence`, `img`, `normal_score`, `hot_score`, `porn_score`, `level`, `polity_score`, `illegal_score`, `terror_score`, `handle_status`, `create_time`, `update_time`, `screenshot_time`, `resume_time`) VALUES (2, 3, 1, 99, 'http://image.imhtb.cn/avatar.png', 1, 0, 99, 0, 0, 0, 0, 1, '2020-05-13 18:23:36', NULL, 1589365415, '2020-05-14 02:23:36');
COMMIT;

-- ----------------------------
-- Table structure for live_info
-- ----------------------------
DROP TABLE IF EXISTS `live_info`;
CREATE TABLE `live_info` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `start_time` datetime DEFAULT NULL,
                             `end_time` datetime DEFAULT NULL,
                             `room_id` int DEFAULT NULL,
                             `user_id` int DEFAULT NULL,
                             `create_time` datetime DEFAULT NULL,
                             `update_time` datetime DEFAULT NULL,
                             `status` int DEFAULT NULL,
                             `click_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `dan_mu_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             `present_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `FK_ROOM_INFO_ROOM_ID` (`room_id`) USING BTREE,
                             CONSTRAINT `FK_ROOM_INFO_ROOM_ID` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of live_info
-- ----------------------------
BEGIN;
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (8, '2020-03-05 18:00:30', '2020-03-05 18:04:41', 1, 10001, '2020-03-05 18:00:30', '2020-03-05 18:04:41', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (9, '2020-03-05 18:05:37', '2020-03-05 18:14:08', 1, 10001, '2020-03-05 18:05:37', '2020-03-05 18:14:08', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (10, '2020-03-18 16:48:33', '2020-03-18 16:49:29', 1, 10001, '2020-03-18 16:48:33', '2020-03-18 16:49:29', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (11, '2020-03-18 16:50:57', '2020-03-18 16:51:18', 1, 10001, '2020-03-18 16:50:57', '2020-03-18 16:51:18', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (12, '2020-03-18 17:43:38', '2020-03-18 17:53:17', 1, 10001, '2020-03-18 17:43:38', '2020-03-18 17:53:17', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (13, '2020-05-10 23:52:49', '2020-05-10 23:54:41', 1, 10001, '2020-05-10 23:52:49', '2020-05-10 23:54:41', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (14, '2020-05-10 23:55:33', '2020-05-10 23:58:12', 1, 10001, '2020-05-10 23:55:33', '2020-05-10 23:58:12', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (15, '2020-05-13 16:28:40', '2020-05-13 16:32:12', 1, 10001, '2020-05-13 16:28:40', '2020-05-13 16:32:12', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (16, '2020-05-13 16:36:12', '2020-05-13 16:38:19', 1, 10001, '2020-05-13 16:36:12', '2020-05-13 16:38:19', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (17, '2020-05-13 16:54:04', '2020-05-13 17:02:34', 1, 10001, '2020-05-13 16:54:04', '2020-05-13 17:02:34', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (18, '2020-05-13 17:12:31', '2020-05-13 17:16:51', 1, 10001, '2020-05-13 17:12:31', '2020-05-13 17:16:51', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (19, '2020-05-13 17:17:25', '2020-05-13 17:19:32', 1, 10001, '2020-05-13 17:17:25', '2020-05-13 17:19:32', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (20, '2020-05-13 17:20:47', '2020-05-13 17:22:32', 1, 10001, '2020-05-13 17:20:47', '2020-05-13 17:22:32', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (21, '2020-05-13 17:27:37', '2020-05-13 17:30:14', 1, 10001, '2020-05-13 17:27:37', '2020-05-13 17:30:14', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (22, '2020-05-13 17:34:48', '2020-05-13 17:37:32', 1, 10001, '2020-05-13 17:34:48', '2020-05-13 17:37:32', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (23, '2020-05-13 17:40:28', '2020-05-13 17:45:28', 1, 10001, '2020-05-13 17:40:28', '2020-05-13 17:45:28', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (24, '2020-05-13 17:53:46', '2020-05-13 17:54:20', 1, 10001, '2020-05-13 17:53:46', '2020-05-13 17:54:20', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (25, '2020-05-13 18:10:54', '2020-05-13 18:11:36', 1, 10001, '2020-05-13 18:10:54', '2020-05-13 18:11:36', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (26, '2020-05-13 18:13:22', '2020-05-13 18:13:59', 1, 10001, '2020-05-13 18:13:22', '2020-05-13 18:13:59', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (27, '2020-05-13 18:15:06', '2020-05-13 18:15:15', 1, 10001, '2020-05-13 18:15:06', '2020-05-13 18:15:15', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (28, '2020-05-13 18:15:55', '2020-05-13 18:16:02', 1, 10001, '2020-05-13 18:15:55', '2020-05-13 18:16:02', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (29, '2020-05-13 18:17:21', '2020-05-13 18:17:51', 1, 10001, '2020-05-13 18:17:21', '2020-05-13 18:17:51', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (30, '2020-05-13 18:23:32', '2020-05-13 18:23:43', 1, 10001, '2020-05-13 18:23:32', '2020-05-13 18:23:43', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (31, '2020-05-17 10:56:34', '2020-05-17 10:59:52', 1, 10001, '2020-05-17 10:56:34', '2020-05-17 10:59:52', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (32, '2020-05-19 09:22:12', '2020-05-19 09:23:01', 1, 10001, '2020-05-19 09:22:12', '2020-05-19 09:23:01', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (33, '2020-05-20 15:55:07', '2020-05-20 15:57:59', 1, 10001, '2020-05-20 15:55:07', '2020-05-20 15:57:59', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (34, '2020-05-20 15:58:41', '2020-05-20 15:59:36', 1, 10001, '2020-05-20 15:58:41', '2020-05-20 15:59:36', 1, NULL, NULL, NULL);
INSERT INTO `live_info` (`id`, `start_time`, `end_time`, `room_id`, `user_id`, `create_time`, `update_time`, `status`, `click_count`, `dan_mu_count`, `present_count`) VALUES (35, '2020-05-20 16:59:16', '2020-05-20 16:59:30', 1, 10001, '2020-05-20 16:59:16', '2020-05-20 16:59:30', 1, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `menu_index` int DEFAULT NULL,
                        `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `pid` int DEFAULT '0',
                        `sort` int DEFAULT NULL,
                        `hidden` int DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (1, 1, 'el-icon-data-board', 'dashboard', '首页', 0, 1, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (2, 2, 'el-icon-user', 'user-manage', '会员中心', 0, 2, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (3, 3, 'el-icon-coordinate', 'user-auth', '身份验证', 0, 3, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (4, 4, 'el-icon-bangzhu', 'live-room-manage', '直播管理', 16, 4, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (5, 5, 'el-icon-data-analysis', 'live-info-manage', '直播数据', 16, 5, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (6, 6, 'el-icon-menu', 'system-settings', '系统设置', 0, 6, 1, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (7, 7, 'el-icon-data-analysis', 'data-analysis', '数据统计', 0, 90, 0, NULL, '2020-05-23 19:41:03');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (8, 8, 'el-icon-goods', 'present-manage', '礼物配置', 0, 8, 0, NULL, '2020-05-27 11:18:51');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (9, 9, 'el-icon-s-shop', 'live-ban-manage', '小黑屋', 16, 9, 1, NULL, '2020-05-23 19:48:26');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (10, 10, 'el-icon-chat-line-round', 'message-push', '消息推送', 0, 88, 0, NULL, '2020-05-09 11:25:27');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (11, 11, 'el-icon-data-analysis', 'system-monitor-host', '服务监控', 12, 11, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (12, 12, 'el-icon-warning-outline', 'system-monitor', '系统监控', 0, 18, 0, NULL, '2020-05-27 11:18:18');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (13, 13, 'el-icon-data-analysis', 'system-manage', '系统管理', 0, 13, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (14, 14, 'el-icon-data-analysis', 'system-manage-menu', '菜单管理', 13, 14, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (15, 15, 'el-icon-data-analysis', 'system-manage-role', '角色管理', 13, 15, 0, NULL, NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (16, 16, 'el-icon-video-camera', 'live-center', '直播中心', 0, 16, 0, NULL, '2020-05-09 11:25:52');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (17, 17, 'el-icon-data-board', 'live-detect', '截图检测', 16, 6, 0, '2020-05-08 14:36:26', '2020-05-23 19:47:54');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (18, 18, 'el-icon-data-analysis', 'bill', '账单中心', 0, 4, 0, '2020-05-09 11:11:18', NULL);
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (19, 19, 'el-icon-user', 'user-role-manage', '角色分配', 13, 1, 0, '2020-05-09 17:24:39', '2020-05-19 11:39:04');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (20, 20, 'el-icon-data-board', 'snapshot-templates', '鉴黄模板', 16, 1, 0, '2020-05-22 14:32:58', '2020-05-23 19:15:10');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (21, 21, 'el-icon-warning-outline', 'ban-record', '封禁记录', 16, 1, 0, '2020-05-23 18:27:47', '2020-05-23 19:15:05');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (22, NULL, 'el-icon-data-analysis', 'vod', '视频中心', 0, 17, 0, '2020-05-25 16:13:02', '2020-05-27 11:17:53');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (23, NULL, 'el-icon-data-analysis', 'video-manage', '视频管理', 22, 1, 0, '2020-05-25 16:13:51', '2020-05-27 10:25:21');
INSERT INTO `menu` (`id`, `menu_index`, `icon`, `path`, `title`, `pid`, `sort`, `hidden`, `create_time`, `update_time`) VALUES (24, NULL, 'el-icon-data-analysis', 'vod-list', '稿件列表', 22, 2, 0, '2020-05-27 11:51:19', NULL);
COMMIT;

-- ----------------------------
-- Table structure for present
-- ----------------------------
DROP TABLE IF EXISTS `present`;
CREATE TABLE `present`
(
    `id`          int            NOT NULL AUTO_INCREMENT,
    `name`        varchar(255)   NOT NULL,
    `icon`        varchar(255)   NOT NULL,
    `price`       decimal(10, 2) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `sort`        int          DEFAULT 99,
    `disabled`    int          DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of present
-- ----------------------------
BEGIN;
INSERT INTO `present` (`id`, `name`, `icon`, `price`, `description`, `create_time`, `update_time`, `sort`, `disabled`)
VALUES (1, '火箭', 'http://image.imhtb.cn/飞机.png', 10.00, '爱她就送她一个火箭', '2020-02-26 18:20:48',
        '2020-05-25 22:34:46', 1, 0);
INSERT INTO `present` (`id`, `name`, `icon`, `price`, `description`, `create_time`, `update_time`, `sort`, `disabled`)
VALUES (2, '飞机', 'http://image.imhtb.cn/飞机1.png', 88.00, '爱她就送她一个飞机', '2020-03-02 10:01:35',
        '2020-05-25 22:34:39', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for present_reward
-- ----------------------------
DROP TABLE IF EXISTS `present_reward`;
CREATE TABLE `present_reward` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `from_id` int DEFAULT NULL,
                                  `to_id` int DEFAULT NULL,
                                  `room_id` int DEFAULT NULL,
                                  `video_id` int DEFAULT NULL,
                                  `present_id` int DEFAULT NULL,
                                  `number` int DEFAULT NULL,
                                  `unit_price` decimal(10,2) DEFAULT NULL,
                                  `total_price` decimal(10,2) DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  `type` int DEFAULT NULL COMMENT '0-为直播间礼物 1-为视频打赏礼物',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `FK_ROOM_PRESENT_ROOM_ID` (`room_id`) USING BTREE,
                                  CONSTRAINT `FK_ROOM_PRESENT_ROOM_ID` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of present_reward
-- ----------------------------
BEGIN;
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (5, 10002, 10001, 2, NULL, 2, 1, 88000.00, 88000.00, '2020-04-06 19:54:37', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (6, 10002, 10001, 2, NULL, 1, 1, 1000.00, 1000.00, '2020-04-06 19:55:04', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (8, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:18:32', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (9, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:19:11', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (10, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:19:30', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (11, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:20:24', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (12, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:20:31', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (13, 10001, 10001, 1, NULL, 1, 1, 10.00, 10.00, '2020-04-11 13:23:04', NULL, NULL);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (17, 10001, 10002, 2, NULL, 1, 1, 10.00, 10.00, '2020-05-25 22:47:13', NULL, 1);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (18, 10002, 10001, NULL, 1, 1, 100, 10.00, 1000.00, '2020-05-26 11:26:58', NULL, 1);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (19, 10001, 10002, 2, NULL, 1, 10, 10.00, 100.00, '2020-05-26 11:29:53', NULL, 0);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (20, 10001, 10002, 2, NULL, 1, 10, 10.00, 100.00, '2020-05-26 11:30:09', NULL, 0);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (21, 10001, 10002, 2, NULL, 1, 100, 10.00, 1000.00, '2020-05-26 11:30:43', NULL, 0);
INSERT INTO `present_reward` (`id`, `from_id`, `to_id`, `room_id`, `video_id`, `present_id`, `number`, `unit_price`, `total_price`, `create_time`, `update_time`, `type`) VALUES (22, 10001, 10001, NULL, 2, 1, 1, 10.00, 10.00, '2021-11-23 23:29:42', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `level` int DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (1, '超级管理员', 1, '2020-04-30 13:57:24', '2020-04-30 13:57:27', 'ROLE_ROOT');
INSERT INTO `role` (`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (2, '直播管理员', 2, '2020-04-30 13:57:46', '2020-04-30 13:57:48', 'ROLE_LIVE');
INSERT INTO `role` (`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (100, '普通会员', 8, '2020-04-30 15:16:56', '2020-04-30 15:16:58', 'ROLE_COMMON');
INSERT INTO `role` (`id`, `name`, `level`, `create_time`, `update_time`, `permission`) VALUES (101, '身份认证管理员', 2, '2020-05-09 11:21:03', NULL, 'ROLE_AUTH');
COMMIT;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
                             `role_id` int NOT NULL,
                             `menu_id` int NOT NULL,
                             `create_time` datetime DEFAULT NULL,
                             `update_time` datetime DEFAULT NULL,
                             PRIMARY KEY (`role_id`,`menu_id`) USING BTREE,
                             KEY `FK_ROLE_MENU_MENU_ID` (`menu_id`) USING BTREE,
                             CONSTRAINT `FK_ROLE_MENU_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT `FK_ROLE_MENU_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (101, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (101, 3);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 4);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 5);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 5);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 7);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 8);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 9);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 9);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 10);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 11);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 12);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 13);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 14);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 15);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 16);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 16);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 17);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 17);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 18);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 19);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 20);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 20);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 21);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (2, 21);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 22);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 23);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 24);
COMMIT;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg',
                        `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `notice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `user_id` int unsigned DEFAULT NULL,
                        `rtmp_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `disabled` int DEFAULT '0',
                        `status` int DEFAULT '-1',
                        `category_id` int DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `push_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `pull_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `FK_ROOM_CATEGORY_ID` (`category_id`) USING BTREE,
                        CONSTRAINT `FK_ROOM_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of room
-- ----------------------------
BEGIN;
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (1, '', 'Spring Security', 'https://images.unsplash.com/photo-1582917205301-bbb4afb5501f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80', '简介', '通知公告', 10001, 'http://play.imhtb.cn/live/', 0, 0, 1, '2020-03-04 06:40:32', '2020-05-22 13:37:29', '2');
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (2, '', '官方直播间', 'https://images.unsplash.com/photo-1579599709180-375ce2a5db8b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2125&q=80', '这里是官方直播间', '公告：24小时不间断直播', 10002, 'http://play.imhtb.cn/live/', 0, 1, 1, '2020-03-02 10:36:16', '2020-05-13 18:06:20', '2');
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (5, NULL, '哈哈', 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/temp-15889987327273642532524691202410.jpg', NULL, '哈哈', 10007, NULL, 0, 0, 1, '2020-05-09 11:58:29', '2020-05-13 18:06:19', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (6, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10008, NULL, 0, -1, NULL, '2020-05-13 17:34:19', '2020-05-13 17:34:19', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (7, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10009, NULL, 0, 0, NULL, '2020-05-18 12:52:48', '2020-05-18 13:50:06', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (8, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10010, NULL, 0, -1, NULL, '2020-05-27 16:58:14', '2020-05-27 16:58:14', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (9, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10011, NULL, 0, -1, NULL, '2020-05-27 17:01:37', '2020-05-27 17:01:37', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (10, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10012, NULL, 0, -1, NULL, '2020-05-27 17:03:04', '2020-05-27 17:03:04', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (11, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10013, NULL, 0, -1, NULL, '2020-05-27 17:04:30', '2020-05-27 17:04:30', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (12, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10014, NULL, 0, -1, NULL, '2020-05-27 17:06:09', '2020-05-27 17:06:09', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (13, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10015, NULL, 0, -1, NULL, '2020-05-27 17:08:06', '2020-05-27 17:08:06', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (14, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10016, NULL, 0, -1, NULL, '2020-05-27 17:10:31', '2020-05-27 17:10:31', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (15, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10017, NULL, 0, -1, NULL, '2020-05-27 17:14:41', '2020-05-27 17:14:41', NULL);
INSERT INTO `room` (`id`, `name`, `title`, `cover`, `introduce`, `notice`, `user_id`, `rtmp_url`, `disabled`, `status`, `category_id`, `create_time`, `update_time`, `secret`) VALUES (16, NULL, NULL, 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/room-default-cover.jpeg', NULL, NULL, 10018, NULL, 0, -1, NULL, '2020-05-27 17:57:45', '2020-05-27 17:57:45', NULL);
COMMIT;

-- ----------------------------
-- Table structure for statistic_speak
-- ----------------------------
DROP TABLE IF EXISTS `statistic_speak`;
CREATE TABLE `statistic_speak` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `room_id` int DEFAULT NULL,
                                   `user_id` int DEFAULT NULL,
                                   `number` int DEFAULT NULL,
                                   `date` date DEFAULT NULL,
                                   `create_time` datetime DEFAULT NULL,
                                   `update_time` datetime DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of statistic_speak
-- ----------------------------
BEGIN;
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (2, 1, NULL, 11, '2020-04-26', '2020-04-27 00:01:02', '2020-04-27 00:01:02');
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (3, 1, NULL, 33, '2020-04-26', '2020-04-27 00:07:00', '2020-04-27 00:07:00');
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (4, 1, NULL, 5, '2020-05-12', '2020-05-13 08:00:05', '2020-05-13 08:00:05');
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (5, 1, NULL, 1, '2020-05-17', '2020-05-18 08:00:05', '2020-05-18 08:00:05');
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (6, 1, NULL, 1, '2020-05-19', '2020-05-20 08:00:06', '2020-05-20 08:00:06');
INSERT INTO `statistic_speak` (`id`, `room_id`, `user_id`, `number`, `date`, `create_time`, `update_time`) VALUES (7, 1, NULL, 2, '2020-05-20', '2020-05-21 08:00:05', '2020-05-21 08:00:05');
COMMIT;

-- ----------------------------
-- Table structure for statistic_view
-- ----------------------------
DROP TABLE IF EXISTS `statistic_view`;
CREATE TABLE `statistic_view` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `room_id` int DEFAULT NULL,
                                  `user_id` int DEFAULT NULL,
                                  `member_number` int DEFAULT NULL,
                                  `visitor_number` int DEFAULT NULL COMMENT '游客浏览数',
                                  `total_number` int DEFAULT NULL,
                                  `date` date DEFAULT NULL,
                                  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of statistic_view
-- ----------------------------
BEGIN;
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (1, 1, 1, 123, 23, 146, '2020-04-12', '', '2020-04-12 13:31:27', NULL);
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (2, 1, 1, 23, 23, 46, '2020-04-08', '', '2020-04-12 13:31:27', NULL);
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (3, 1, NULL, 0, 5, 5, '2020-04-26', NULL, '2020-04-27 00:07:00', '2020-04-27 00:07:00');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (4, 2, NULL, 0, 4, 4, '2020-05-05', NULL, '2020-05-06 00:00:08', '2020-05-06 00:00:08');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (5, 2, NULL, 0, 5, 5, '2020-05-09', NULL, '2020-05-10 08:00:05', '2020-05-10 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (6, 1, NULL, 0, 8, 5, '2020-05-10', NULL, '2020-05-11 08:00:05', '2020-05-11 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (7, 1, NULL, 0, 3, 3, '2020-05-11', NULL, '2020-05-12 08:00:05', '2020-05-12 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (8, 2, NULL, 0, 1, 1, '2020-05-11', NULL, '2020-05-12 08:00:05', '2020-05-12 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (9, 2, NULL, 0, 1, 1, '2020-05-12', NULL, '2020-05-13 08:00:05', '2020-05-13 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (10, 1, NULL, 0, 2, 2, '2020-05-12', NULL, '2020-05-13 08:00:05', '2020-05-13 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (11, 1, NULL, 0, 3, 3, '2020-05-13', NULL, '2020-05-14 08:00:05', '2020-05-14 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (12, 1, NULL, 0, 1, 1, '2020-05-17', NULL, '2020-05-18 08:00:05', '2020-05-18 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (13, 1, NULL, 0, 1, 1, '2020-05-18', NULL, '2020-05-19 08:00:05', '2020-05-19 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (14, 1, NULL, 0, 1, 1, '2020-05-19', NULL, '2020-05-20 08:00:06', '2020-05-20 08:00:06');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (15, 1, NULL, 0, 3, 3, '2020-05-20', NULL, '2020-05-21 08:00:05', '2020-05-21 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (16, 2, NULL, 0, 7, 7, '2020-05-21', NULL, '2020-05-22 08:00:05', '2020-05-22 08:00:05');
INSERT INTO `statistic_view` (`id`, `room_id`, `user_id`, `member_number`, `visitor_number`, `total_number`, `date`, `mark`, `create_time`, `update_time`) VALUES (17, 2, NULL, 0, 1, 1, '2020-05-22', NULL, '2020-05-23 08:00:05', '2020-05-23 08:00:05');
COMMIT;

-- ----------------------------
-- Table structure for sys_push
-- ----------------------------
DROP TABLE IF EXISTS `sys_push`;
CREATE TABLE `sys_push` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            `user_id` int DEFAULT NULL,
                            `open` int DEFAULT '0',
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `listener_items` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `FK_SYSTEM_PUSH_USER_ID` (`user_id`) USING BTREE,
                            CONSTRAINT `FK_SYSTEM_PUSH_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_push
-- ----------------------------
BEGIN;
INSERT INTO `sys_push` (`id`, `email`, `mobile`, `user_id`, `open`, `create_time`, `update_time`, `listener_items`) VALUES (2, '794409767@qq.com', NULL, 10001, 1, '2020-05-08 18:02:13', '2020-05-27 18:13:00', 'salacity-notice');
COMMIT;

-- ----------------------------
-- Table structure for sys_push_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_push_log`;
CREATE TABLE `sys_push_log` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                `status` int DEFAULT NULL,
                                `create_time` datetime DEFAULT NULL,
                                `update_time` datetime DEFAULT NULL,
                                `sys_push_id` int DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_push_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_push_log` (`id`, `content`, `status`, `create_time`, `update_time`, `sys_push_id`) VALUES (1, '房间ID：10001 色情检测置信度99', 1, '2020-05-15 16:14:44', '2020-05-20 16:14:42', 2);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci,
                        `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                        `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'https://ant-live-store-1253825991.cos.ap-chengdu.myqcloud.com/gray_avatar.jpg',
                        `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `is_validated` int DEFAULT '0',
                        `disabled` int DEFAULT '0',
                        `role_id` int NOT NULL DEFAULT '100',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10019 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `mobile`, `avatar`, `signature`, `birthday`, `sex`, `nickname`, `create_time`, `update_time`, `email`, `is_validated`, `disabled`, `role_id`) VALUES (10001, 'admin', '$2a$10$puULYxVheVu/sJZk7rUbvujNheV9v7afPWETHv47sjS2KAXNptTEe', '', 'http://image.imhtb.cn/avatar.png', '个性签名', NULL, '男', 'PinTeh', '2020-05-09 18:47:23', '2020-05-09 11:33:16', '794409767@qq.com', 0, 0, 1);
INSERT INTO `user` (`id`, `username`, `password`, `mobile`, `avatar`, `signature`, `birthday`, `sex`, `nickname`, `create_time`, `update_time`, `email`, `is_validated`, `disabled`, `role_id`) VALUES (10002, 'demo', '$2a$10$GU9Ya.QrkZu.0TNxO4BuG.B9x26pD7Yl8jQUTENz3OuB3mBTqlWeC', NULL, 'http://q1.qlogo.cn/g?b=qq&nk=363353005&s=100', '个性签名', NULL, '女', '官方直播账号', '2020-05-09 18:47:25', '2020-04-30 23:20:45', '3633530052@qq.com', 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             `user_id` int NOT NULL COMMENT '用户ID',
                             `role_id` int NOT NULL COMMENT '角色ID',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
                             KEY `FK_USER_ROLE_ROLE_ID` (`role_id`) USING BTREE,
                             CONSTRAINT `FK_USER_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT `FK_USER_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` (`user_id`, `role_id`, `create_time`, `update_time`) VALUES (10001, 1, NULL, NULL);
INSERT INTO `user_role` (`user_id`, `role_id`, `create_time`, `update_time`) VALUES (10002, 2, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `video_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                         `cover_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                         `file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                         `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                         `user_id` int DEFAULT NULL,
                         `video_category_id` int DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                         `status` int DEFAULT '0',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of video
-- ----------------------------
BEGIN;
INSERT INTO `video` (`id`, `video_url`, `cover_url`, `file_id`, `type`, `user_id`, `video_category_id`, `create_time`, `update_time`, `title`, `status`) VALUES (1, 'http://1253825991.vod2.myqcloud.com/a62a57cfvodcq1253825991/d444c50c5285890802997421440/uCtVi1NBRxUA.mp4', 'https://images.unsplash.com/photo-1579599709180-375ce2a5db8b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2125&q=80', '5285890802997421440', NULL, 10001, NULL, '2020-05-25 15:00:29', '2020-05-27 18:18:16', 'Spring Redis Startar', 0);
INSERT INTO `video` (`id`, `video_url`, `cover_url`, `file_id`, `type`, `user_id`, `video_category_id`, `create_time`, `update_time`, `title`, `status`) VALUES (2, 'http://1253825991.vod2.myqcloud.com/a62a57cfvodcq1253825991/8bd2d61b5285890803425878758/z1otW652Nw0A.mp4', 'https://images.unsplash.com/photo-1579599709180-375ce2a5db8b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2125&q=80', '5285890803425878758', NULL, 10001, NULL, '2020-05-25 15:22:59', '2020-05-27 18:18:05', 'Spring Security', 0);
COMMIT;

-- ----------------------------
-- Table structure for watch
-- ----------------------------
DROP TABLE IF EXISTS `watch`;
CREATE TABLE `watch` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int DEFAULT NULL,
                         `room_id` int DEFAULT NULL,
                         `watch_type` int DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE KEY `idx_uid_rid_wt` (`user_id`,`room_id`,`watch_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of watch
-- ----------------------------
BEGIN;
INSERT INTO `watch` (`id`, `user_id`, `room_id`, `watch_type`, `create_time`, `update_time`) VALUES (11, 10001, 2, 1, '2021-11-23 23:30:49', '2021-11-23 23:30:49');
COMMIT;

-- ----------------------------
-- Table structure for withdrawal
-- ----------------------------
DROP TABLE IF EXISTS `withdrawal`;
CREATE TABLE `withdrawal` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `identity` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收款账号',
                              `identity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提现用户名',
                              `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
                              `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'alipay - wechat',
                              `user_id` int DEFAULT NULL COMMENT '用户id',
                              `status` int DEFAULT '0' COMMENT '当前状态 0-未完成 1-完成',
                              `create_time` datetime DEFAULT NULL,
                              `update_time` datetime DEFAULT NULL,
                              `virtual_amount` decimal(10,2) DEFAULT NULL COMMENT '提现金豆',
                              `real_amount` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of withdrawal
-- ----------------------------
BEGIN;
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (1, '123', '123', 'mark', 'alipay', 10001, 0, '2020-04-09 15:53:12', '2020-04-09 15:53:14', 1000.00, 100.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (2, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', 'alipay', 10001, 1, '2020-04-09 23:30:46', NULL, 100.00, 10.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (3, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', 'alipay', 10001, 1, '2020-04-09 23:39:13', NULL, 100.00, 10.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (4, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', 'alipay', 10001, 1, '2020-04-10 00:12:12', NULL, 100.00, 10.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (5, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', 'alipay', 10001, 1, '2020-04-10 00:15:09', NULL, 100.00, 10.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (6, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', 'alipay', 10001, 1, '2020-04-10 00:20:50', NULL, 100.00, 10.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (7, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', '支付宝', 10001, 1, '2020-04-11 12:13:43', NULL, 10.00, 1.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (8, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', '支付宝', 10001, 1, '2020-05-11 23:54:40', '2020-05-11 23:54:40', 90.00, 18.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (9, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', '支付宝', 10001, 1, '2020-05-12 00:01:40', '2020-05-12 00:01:40', 100.00, 5.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (10, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', '支付宝', 10001, 1, '2020-05-12 00:24:03', '2020-05-12 00:24:03', 440.00, 22.00);
INSERT INTO `withdrawal` (`id`, `identity`, `identity_name`, `mark`, `type`, `user_id`, `status`, `create_time`, `update_time`, `virtual_amount`, `real_amount`) VALUES (11, 'ihydlk5321@sandbox.com', '沙箱环境', '提现', '支付宝', 10001, 1, '2020-05-20 16:07:35', '2020-05-20 16:07:35', 300.00, 15.00);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `tb_wallet`;
CREATE TABLE `tb_wallet`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     int unsigned NOT NULL COMMENT '关联用户id',
    `balance`     decimal(10, 2)        DEFAULT NULL COMMENT '余额',
    `currency`    varchar(64)           DEFAULT NULL COMMENT '币种',
    `sign`        varchar(64)           DEFAULT NULL COMMENT '签名',
    `version`     int          NOT NULL DEFAULT 0 COMMENT '版本号',
    `status`      int          NOT NULL DEFAULT 0 COMMENT '状态 0-正常 1-异常',
    `create_time` datetime              DEFAULT NULL,
    `update_time` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_user_id` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='钱包表';

DROP TABLE IF EXISTS `tb_wallet_log`;
CREATE TABLE `tb_wallet_log`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `wallet_id`   int(10) unsigned NOT NULL COMMENT '关联钱包id',
    `balance`     decimal(10, 2) DEFAULT NULL COMMENT '余额',
    `fee`         varchar(64)    DEFAULT NULL COMMENT '变换金额',
    `source_uuid` varchar(64)    DEFAULT NULL COMMENT '业务来源id',
    `source_type` varchar(64)    DEFAULT NULL COMMENT '业务来源类型',
    `action_type` int(5)         DEFAULT NULL COMMENT '操作类型',
    `create_time` datetime       DEFAULT NULL,
    `update_time` datetime       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='钱包记录表';