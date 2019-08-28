/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2018-06-05 19:36:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bulletins
-- ----------------------------
DROP TABLE IF EXISTS `bulletins`;
CREATE TABLE `bulletins` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `e_id` varchar(50) NOT NULL,
  `time` datetime NOT NULL,
  `likes` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `e_id` (`e_id`),
  CONSTRAINT `e_id` FOREIGN KEY (`e_id`) REFERENCES `enterpriseuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bulletins
-- ----------------------------
INSERT INTO `bulletins` VALUES ('5', '234234', '234234', '123123', '2018-06-03 21:39:40', '0');
INSERT INTO `bulletins` VALUES ('6', '123', '2456', '123123', '2018-06-04 19:44:00', '0');

-- ----------------------------
-- Table structure for b_like
-- ----------------------------
DROP TABLE IF EXISTS `b_like`;
CREATE TABLE `b_like` (
  `b_id` int(11) NOT NULL,
  `u_id` varchar(255) NOT NULL,
  PRIMARY KEY  (`b_id`,`u_id`),
  KEY `uu_id` (`u_id`),
  CONSTRAINT `bu_id` FOREIGN KEY (`b_id`) REFERENCES `bulletins` (`id`),
  CONSTRAINT `uu_id` FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_like
-- ----------------------------

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `m_id` int(11) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `time` datetime NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `p_id` (`user_id`),
  KEY `m_id` (`m_id`),
  CONSTRAINT `m_id` FOREIGN KEY (`m_id`) REFERENCES `moments` (`id`),
  CONSTRAINT `p_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('2', '123', '12333', '2018-06-02 19:02:17', '3');
INSERT INTO `comments` VALUES ('2', '12312322', '12333', '2018-06-02 19:02:34', '4');
INSERT INTO `comments` VALUES ('2', '123', '测试评论', '2018-06-03 00:20:32', '5');
INSERT INTO `comments` VALUES ('2', '123', '123123', '2018-06-04 12:38:20', '11');
INSERT INTO `comments` VALUES ('2', '123', '阿瑟东', '2018-06-05 00:01:16', '14');

-- ----------------------------
-- Table structure for enterpriseuser
-- ----------------------------
DROP TABLE IF EXISTS `enterpriseuser`;
CREATE TABLE `enterpriseuser` (
  `id` varchar(50) NOT NULL,
  `subject` varchar(50) default NULL,
  `pw` varchar(50) NOT NULL,
  `headimg` varchar(50) default NULL,
  `name` varchar(50) NOT NULL,
  `introduction` text,
  `telephone` varchar(50) default NULL,
  `scopeOperation` varchar(50) default NULL,
  `mail` varchar(50) NOT NULL,
  `state` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enterpriseuser
-- ----------------------------
INSERT INTO `enterpriseuser` VALUES ('123123', '123', '123', '123', '123', '我是帅哥', '13413622230', '123', '123', '1');
INSERT INTO `enterpriseuser` VALUES ('456', '456', '456', '456', '456', '456', '456', '456', '456', '1');

-- ----------------------------
-- Table structure for focus
-- ----------------------------
DROP TABLE IF EXISTS `focus`;
CREATE TABLE `focus` (
  `u_id` varchar(50) NOT NULL,
  `e_id` varchar(50) NOT NULL,
  PRIMARY KEY  (`u_id`,`e_id`),
  KEY `e_id1` (`e_id`),
  CONSTRAINT `e_id1` FOREIGN KEY (`e_id`) REFERENCES `enterpriseuser` (`id`),
  CONSTRAINT `u_id1` FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of focus
-- ----------------------------
INSERT INTO `focus` VALUES ('123', '123123');

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `u_id` varchar(50) NOT NULL,
  `f_id` varchar(50) NOT NULL,
  `state` int(4) NOT NULL,
  `sessionstate` int(11) NOT NULL,
  `sessiontime` datetime default NULL,
  PRIMARY KEY  (`u_id`,`f_id`),
  KEY `f_id` (`f_id`),
  CONSTRAINT `f_id` FOREIGN KEY (`f_id`) REFERENCES `user` (`id`),
  CONSTRAINT `u_id` FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friends
-- ----------------------------
INSERT INTO `friends` VALUES ('123', '12312322', '1', '1', '2018-05-31 23:30:33');
INSERT INTO `friends` VALUES ('123', '123123456', '0', '1', '2018-06-01 01:26:10');
INSERT INTO `friends` VALUES ('12312322', '123', '0', '1', '2018-06-01 18:55:01');
INSERT INTO `friends` VALUES ('123123456', '123', '1', '1', null);

-- ----------------------------
-- Table structure for groupmember
-- ----------------------------
DROP TABLE IF EXISTS `groupmember`;
CREATE TABLE `groupmember` (
  `g_id` int(11) NOT NULL,
  `member_id` varchar(50) NOT NULL,
  KEY `g_id` (`g_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `g_id` FOREIGN KEY (`g_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `member_id` FOREIGN KEY (`member_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupmember
-- ----------------------------

-- ----------------------------
-- Table structure for groupmessages
-- ----------------------------
DROP TABLE IF EXISTS `groupmessages`;
CREATE TABLE `groupmessages` (
  `g_id` int(11) NOT NULL,
  `sender_id` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `sendtime` datetime NOT NULL,
  KEY `group_id` (`g_id`),
  CONSTRAINT `group_id` FOREIGN KEY (`g_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupmessages
-- ----------------------------

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL auto_increment,
  `owner_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `bullein` text,
  PRIMARY KEY  (`id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `owner_id` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES ('2', '12312322', '123', '123');
INSERT INTO `groups` VALUES ('11', '123', '33333333333', '33');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL auto_increment,
  `sender_id` varchar(50) NOT NULL,
  `sendee_id` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `type` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `sendtime` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `sendee_id` (`sendee_id`),
  CONSTRAINT `sendee_id` FOREIGN KEY (`sendee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of messages
-- ----------------------------
INSERT INTO `messages` VALUES ('1', '12312322', '123', '333', '1', '1', '2018-06-02 12:08:19');
INSERT INTO `messages` VALUES ('2', '12312322', '123', 'test', '1', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('3', '123', '12312322', '/img/group.png', '3', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('4', '12312322', '123', '/img/e3.png', '3', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('5', '123', '12312322', '123', '1', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('6', '123', '12312322', '222', '1', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('7', '123', '12312322', '1222', '1', '1', '2018-06-02 00:00:00');
INSERT INTO `messages` VALUES ('8', '123', '12312322', '6666', '1', '1', '2018-06-02 14:43:49');
INSERT INTO `messages` VALUES ('9', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\新建 Microsoft Word 文档.docx', '5', '1', '2018-06-03 03:43:06');
INSERT INTO `messages` VALUES ('10', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.png', '5', '1', '2018-06-03 04:11:57');
INSERT INTO `messages` VALUES ('11', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\pom.xml', '5', '1', '2018-06-03 04:34:06');
INSERT INTO `messages` VALUES ('12', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 04:36:00');
INSERT INTO `messages` VALUES ('13', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.gif', '5', '1', '2018-06-03 04:36:43');
INSERT INTO `messages` VALUES ('14', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\pic_list.xml', '5', '1', '2018-06-03 04:40:53');
INSERT INTO `messages` VALUES ('15', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.gif', '5', '1', '2018-06-03 04:46:02');
INSERT INTO `messages` VALUES ('16', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\pom.xml', '5', '1', '2018-06-03 04:49:23');
INSERT INTO `messages` VALUES ('17', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\user.xml', '5', '1', '2018-06-03 04:51:03');
INSERT INTO `messages` VALUES ('18', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 05:00:04');
INSERT INTO `messages` VALUES ('19', '123', '12312322', '/img/e2.png', '3', '1', '2018-06-03 05:00:24');
INSERT INTO `messages` VALUES ('20', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.gif', '5', '1', '2018-06-03 05:00:32');
INSERT INTO `messages` VALUES ('21', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 05:08:40');
INSERT INTO `messages` VALUES ('22', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\tett.txt', '5', '1', '2018-06-03 05:08:52');
INSERT INTO `messages` VALUES ('23', '123', '12312322', '请问请问请问', '1', '1', '2018-06-03 05:09:09');
INSERT INTO `messages` VALUES ('24', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 13:46:21');
INSERT INTO `messages` VALUES ('25', '123', '12312322', 'wechat\\123\\voice\\\\1528004787349.mp3', '4', '1', '2018-06-03 13:46:27');
INSERT INTO `messages` VALUES ('26', '123', '12312322', 'wechat\\123\\voice\\1528005333795.mp3', '4', '1', '2018-06-03 13:55:33');
INSERT INTO `messages` VALUES ('27', '123', '12312322', 'wechat\\123\\voice\\1528005520354.mp3', '4', '1', '2018-06-03 13:58:40');
INSERT INTO `messages` VALUES ('28', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 14:00:18');
INSERT INTO `messages` VALUES ('29', '123', '12312322', 'wechat\\123\\voice\\1528005624638.mp3', '4', '1', '2018-06-03 14:00:24');
INSERT INTO `messages` VALUES ('30', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 14:26:09');
INSERT INTO `messages` VALUES ('31', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 14:26:59');
INSERT INTO `messages` VALUES ('32', '123', '12312322', '1231232', '1', '1', '2018-06-03 14:27:18');
INSERT INTO `messages` VALUES ('33', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.gif', '5', '1', '2018-06-03 14:27:34');
INSERT INTO `messages` VALUES ('34', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 14:28:17');
INSERT INTO `messages` VALUES ('35', '123', '12312322', '/img/e2.png', '3', '1', '2018-06-03 14:28:25');
INSERT INTO `messages` VALUES ('36', '123', '12312322', 'C:\\Users\\wuzhuhao\\Desktop\\666.gif', '5', '1', '2018-06-03 14:28:50');
INSERT INTO `messages` VALUES ('37', '12312322', '123', '/img/e3.png', '3', '1', '2018-06-03 14:29:11');
INSERT INTO `messages` VALUES ('38', '123', '12312322', '12312321\r\n', '1', '1', '2018-06-03 14:30:44');
INSERT INTO `messages` VALUES ('39', '123', '123123456', '/img/e3.png', '3', '0', '2018-06-03 22:15:40');
INSERT INTO `messages` VALUES ('40', '123', '123123456', '123', '1', '0', '2018-06-03 22:15:57');
INSERT INTO `messages` VALUES ('41', '123', '123123456', '/img/group.png', '3', '0', '2018-06-03 22:17:31');
INSERT INTO `messages` VALUES ('42', '123', '12312322', '/img/e3.png', '3', '1', '2018-06-03 22:40:27');
INSERT INTO `messages` VALUES ('43', '123', '123123456', '/img/e2.png', '3', '0', '2018-06-04 14:50:19');
INSERT INTO `messages` VALUES ('44', '123', '1234', '/img/e3.png', '3', '0', '2018-06-04 14:50:39');
INSERT INTO `messages` VALUES ('45', '123', '12312322', 'wechat\\123\\voice\\1528099469249.mp3', '4', '1', '2018-06-04 16:04:29');
INSERT INTO `messages` VALUES ('46', '123', '12312322', 'wechat\\123\\voice\\1528099477842.mp3', '4', '1', '2018-06-04 16:04:37');
INSERT INTO `messages` VALUES ('47', '123', '12312322', 'wechat\\123\\voice\\1528099491265.mp3', '4', '1', '2018-06-04 16:04:51');
INSERT INTO `messages` VALUES ('48', '123', '123123456', 'wechat\\123\\voice\\1528101468521.mp3', '4', '0', '2018-06-04 16:37:48');
INSERT INTO `messages` VALUES ('49', '123', '123123456', 'wechat\\123\\voice\\1528101483530.mp3', '4', '0', '2018-06-04 16:38:03');
INSERT INTO `messages` VALUES ('50', '123', '12312322', 'wechat\\123\\voice\\1528101742469.mp3', '4', '1', '2018-06-04 16:42:22');
INSERT INTO `messages` VALUES ('51', '123', '12312322', 'wechat\\123\\voice\\1528101759561.mp3', '4', '1', '2018-06-04 16:42:39');
INSERT INTO `messages` VALUES ('52', '123', '12312322', 'wechat\\123\\voice\\1528101805283.mp3', '4', '1', '2018-06-04 16:43:25');
INSERT INTO `messages` VALUES ('53', '123', '12312322', 'wechat\\123\\voice\\1528101813027.mp3', '4', '1', '2018-06-04 16:43:33');
INSERT INTO `messages` VALUES ('54', '123', '1234', 'wechat\\123\\voice\\1528103065028.mp3', '4', '0', '2018-06-04 17:04:25');
INSERT INTO `messages` VALUES ('55', '123', '1234', 'wechat\\123\\voice\\1528103123788.mp3', '4', '0', '2018-06-04 17:05:23');
INSERT INTO `messages` VALUES ('56', '123', '1234', 'wechat\\123\\voice\\1528103245767.mp3', '4', '0', '2018-06-04 17:07:25');
INSERT INTO `messages` VALUES ('57', '123', '123123456', 'wechat\\123\\voice\\1528106435432.mp3', '4', '0', '2018-06-04 18:00:35');
INSERT INTO `messages` VALUES ('58', '123', '123123456', 'wechat\\123\\voice\\1528107855860.mp3', '4', '0', '2018-06-04 18:24:15');

-- ----------------------------
-- Table structure for moments
-- ----------------------------
DROP TABLE IF EXISTS `moments`;
CREATE TABLE `moments` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` varchar(50) NOT NULL,
  `content` text,
  `time` datetime NOT NULL,
  `img` varchar(500) default NULL,
  `likes` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of moments
-- ----------------------------
INSERT INTO `moments` VALUES ('2', '12312322', '123', '2018-06-02 17:49:30', null, '3');
INSERT INTO `moments` VALUES ('29', '123', '嘿嘿', '2018-06-05 00:10:29', '', '0');

-- ----------------------------
-- Table structure for m_like
-- ----------------------------
DROP TABLE IF EXISTS `m_like`;
CREATE TABLE `m_like` (
  `m_id` int(11) NOT NULL,
  `u_id` varchar(50) NOT NULL,
  PRIMARY KEY  (`m_id`,`u_id`),
  KEY `uuu_id` (`u_id`),
  CONSTRAINT `mm_id` FOREIGN KEY (`m_id`) REFERENCES `moments` (`id`),
  CONSTRAINT `uuu_id` FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_like
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `pw` varchar(50) NOT NULL,
  `headimg` varchar(50) default NULL,
  `name` varchar(50) NOT NULL,
  `message` text,
  `sex` varchar(4) default NULL,
  `state` int(10) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `region` varchar(50) default NULL,
  PRIMARY KEY  (`id`,`mail`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', '123123', 'G:/wechat/headimg/123.jpg', 'wuzhuhao', 'testy', '男', '1', '1376261775@qq.com', '广东-广州');
INSERT INTO `user` VALUES ('12312322', '123123', 'G:/wechat/headimg/123.jpg', '12312322', '666', null, '1', '1322644933@qq.com', '广东-广州');
INSERT INTO `user` VALUES ('123123456', '123123', 'G:/wechat/headimg/123.jpg', '123123456', '777', null, '0', '1376261775@qq.com', '广东-广州');
INSERT INTO `user` VALUES ('1234', '123', 'test', 'test', 'test', '女', '0', '1376261775.qq.com', null);
INSERT INTO `user` VALUES ('12345', '123', '123', '123', 'test', '女', '0', '1376261775.qq.com', null);
DROP TRIGGER IF EXISTS `addlike`;
DELIMITER ;;
CREATE TRIGGER `addlike` AFTER INSERT ON `b_like` FOR EACH ROW  update biletin set likes = likes+1 where id = NEW.b_id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delLike`;
DELIMITER ;;
CREATE TRIGGER `delLike` AFTER DELETE ON `b_like` FOR EACH ROW  update bulletin set likes = likes-1 where id = OLD.b_id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `del_g`;
DELIMITER ;;
CREATE TRIGGER `del_g` BEFORE DELETE ON `groups` FOR EACH ROW  delete from groupmember where g_id = OLD.id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `del`;
DELIMITER ;;
CREATE TRIGGER `del` BEFORE DELETE ON `moments` FOR EACH ROW    delete from comments where m_id = OLD.id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `add_like`;
DELIMITER ;;
CREATE TRIGGER `add_like` AFTER INSERT ON `m_like` FOR EACH ROW  update moments set likes = likes+1 where id = NEW.m_id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `del_like`;
DELIMITER ;;
CREATE TRIGGER `del_like` AFTER DELETE ON `m_like` FOR EACH ROW  update moments set likes = likes-1 where OLD.m_id
;;
DELIMITER ;
