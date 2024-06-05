/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : gym

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 05/06/2024 15:16:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_account` int NOT NULL COMMENT '管理员账号',
  `admin_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员密码',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1001, '123456', 1);
INSERT INTO `admin` VALUES (1002, '123456', 2);
INSERT INTO `admin` VALUES (1003, '123456', 3);

-- ----------------------------
-- Table structure for class_order
-- ----------------------------
DROP TABLE IF EXISTS `class_order`;
CREATE TABLE `class_order`  (
  `class_order_id` int NOT NULL AUTO_INCREMENT COMMENT '报名表id',
  `class_id` int NULL DEFAULT NULL COMMENT '课程id',
  `class_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `coach` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教练',
  `member_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '会员姓名',
  `member_account` int NULL DEFAULT NULL COMMENT '会员账号',
  `class_begin` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '开课时间',
  PRIMARY KEY (`class_order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class_order
-- ----------------------------
INSERT INTO `class_order` VALUES (1, 9, '杠铃操', '杠铃操教练', 'Tylor', 202428018, '2024年06月06日 10:00');
INSERT INTO `class_order` VALUES (3, 4, '运动康复', '运动康复教练', 'Tylor', 202428018, '2024年06月11日 10:00');

-- ----------------------------
-- Table structure for class_table
-- ----------------------------
DROP TABLE IF EXISTS `class_table`;
CREATE TABLE `class_table`  (
  `class_id` int NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `class_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `class_begin` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '开课时间',
  `class_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程时长',
  `coach` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教练',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of class_table
-- ----------------------------
INSERT INTO `class_table` VALUES (1, '增肌', '2024年06月9日 15:00', '60分钟', '增肌教练');
INSERT INTO `class_table` VALUES (2, '瑜伽', '2024年06月10日 10:20', '40分钟', '瑜伽教练');
INSERT INTO `class_table` VALUES (3, '减脂', '2024年06月10日 18:00', '30分钟', '减脂教练');
INSERT INTO `class_table` VALUES (4, '运动康复', '2024年06月11日 10:00', '45分钟', '运动康复教练');
INSERT INTO `class_table` VALUES (5, '综合格斗', '2024年06月11日 15:00', '60分钟', '综合格斗教练');
INSERT INTO `class_table` VALUES (6, '塑形', '2024年06月12日 15:00', '60分钟', '塑形教练');
INSERT INTO `class_table` VALUES (7, '普拉提', '2024年06月12日 17:30', '60分钟', '普拉提教练');
INSERT INTO `class_table` VALUES (8, '爵士舞', '2024年06月13日 09:00', '60分钟', '爵士舞教练');
INSERT INTO `class_table` VALUES (9, '杠铃操', '2024年06月06日 10:00', '60分钟', '杠铃操教练');
INSERT INTO `class_table` VALUES (10, '动感单车', '2024年06月13日 15:00', '45分钟', '动感单车教练');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `employee_account` int NOT NULL COMMENT '员工账号',
  `employee_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `employee_gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '员工性别',
  `employee_age` int NULL DEFAULT NULL COMMENT '员工年龄',
  `entry_time` date NULL DEFAULT NULL COMMENT '入职时间',
  `staff` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '职务',
  `employee_message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`employee_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (101010711, '保洁2', '女', 42, '2024-06-05', '保洁员', '');
INSERT INTO `employee` VALUES (101011927, '教练1', '女', 26, '2024-06-05', '健身教练', '健美冠军');
INSERT INTO `employee` VALUES (101018657, '教练4', '女', 24, '2024-06-05', '健身教练', '职业教练');
INSERT INTO `employee` VALUES (101029932, '教练2', '男', 34, '2024-06-05', '健身教练', '职业教练');
INSERT INTO `employee` VALUES (101033926, '保洁1', '女', 48, '2024-06-05', '保洁员', '模范员工');
INSERT INTO `employee` VALUES (101046939, '教练3', '男', 30, '2024-06-05', '健身教练', '职业教练');

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `equipment_id` int NOT NULL AUTO_INCREMENT COMMENT '器材id',
  `equipment_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '器材名称',
  `equipment_location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '器材位置',
  `equipment_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '器材状态',
  `equipment_message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '器材备注信息',
  PRIMARY KEY (`equipment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES (1, '哑铃1', '1号房间', '正常', '');
INSERT INTO `equipment` VALUES (2, '杠铃1', '2号房间', '损坏', '待维修');
INSERT INTO `equipment` VALUES (3, '跑步机1', '2号房间', '维修中', '联系厂家维修');
INSERT INTO `equipment` VALUES (4, '跑步机2', '2号房间', '正常', '');
INSERT INTO `equipment` VALUES (5, '跑步机3', '2号房间', '正常', '');
INSERT INTO `equipment` VALUES (6, '杠铃1', '1号房间', '正常', '');
INSERT INTO `equipment` VALUES (7, '杠铃2', '1号房间', '正常', '');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `member_account` int NOT NULL COMMENT '会员账号',
  `member_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '123456' COMMENT '会员密码',
  `member_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '会员姓名',
  `member_gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '会员性别',
  `member_age` int NULL DEFAULT NULL COMMENT '会员年龄',
  `member_height` int NULL DEFAULT NULL COMMENT '会员身高',
  `member_weight` int NULL DEFAULT NULL COMMENT '会员体重',
  `member_phone` char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '会员电话',
  `card_time` date NULL DEFAULT NULL COMMENT '办卡时间',
  `card_class` int NULL DEFAULT NULL COMMENT '购买课时',
  `card_next_class` int NULL DEFAULT NULL COMMENT '剩余课时',
  PRIMARY KEY (`member_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (202428018, '123456', 'Tylor', '男', 26, 170, 60, '13626459745', '2024-06-05', 465, 55);
INSERT INTO `member` VALUES (202439243, '123456', '李四', '男', 31, 178, 60, '13131554873', '2024-06-05', 70, 0);
INSERT INTO `member` VALUES (202444582, '123456', 'Jack', '男', 33, 174, 70, '13286542657', '2024-06-05', 70, 70);
INSERT INTO `member` VALUES (202445052, '123456', 'Ava', '女', 28, 168, 40, '13754457488', '2024-06-05', 100, 100);
INSERT INTO `member` VALUES (202445142, '123456', '马六', '男', 23, 165, 45, '13562467956', '2024-06-05', 80, 80);
INSERT INTO `member` VALUES (202457410, '123456', 'Emma', '女', 25, 173, 44, '13786457124', '2024-06-05', 50, 50);
INSERT INTO `member` VALUES (202459941, '123456', 'Lily', '女', 25, 165, 51, '15986457423', '2024-06-05', 30, 30);
INSERT INTO `member` VALUES (202462516, '123456', 'Tom', '男', 24, 182, 88, '13758784959', '2024-06-05', 30, 0);
INSERT INTO `member` VALUES (202468616, '123456', 'Mike', '男', 36, 186, 75, '13786457124', '2024-06-05', 50, 50);
INSERT INTO `member` VALUES (202472368, '123456', '张三', '男', 24, 172, 65, '13515548482', '2024-06-05', 40, 10);
INSERT INTO `member` VALUES (202494830, '123456', '王五', '男', 31, 173, 66, '13154785489', '2024-06-05', 50, 5);

SET FOREIGN_KEY_CHECKS = 1;
