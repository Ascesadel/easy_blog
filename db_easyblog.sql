/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : db_easyblog

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 28/11/2023 14:09:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '博客ID',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default.jpg' COMMENT '封面',
  `category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客名称',
  `category_brief` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客简介',
  `category_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客分类（0:草稿  1:博客）',
  `category_self` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客权限（0:仅自己可见  1:他人也可见）',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10060 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES (10027, 0, 'default.jpg', 'EasyBlog', 'EasyBlog一个可以生成静态页面的博客', '1', '0', 100002, 'dz', '2023-09-08 20:03:12', '2023-11-12 11:07:31');
INSERT INTO `blog_category` VALUES (10028, 4, 'default.jpg', 'EasyBlog文档', 'EasyBlog文档', '0', '1', 100000, 'admin', '2023-09-08 20:03:16', '2023-11-12 10:45:22');
INSERT INTO `blog_category` VALUES (10031, 1, 'cover_20230915125140000000460.jpg', '再来两个', '试试删除', '1', '1', 100000, 'admin', '2023-09-15 12:51:46', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10032, 8, 'cover_20230915015751000000845.jpg', '你干嘛', '使用store传递id', '0', '1', 100002, 'dz', '2023-09-15 13:57:58', '2023-11-12 10:45:22');
INSERT INTO `blog_category` VALUES (10034, 2, 'cover_20230915024222000000289.jpg', '再来一次', '123', '1', '0', 100000, 'admin', '2023-09-15 14:42:26', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10035, 3, 'default.jpg', '怎么突然加了一个', '我不到啊', '1', '1', 100000, 'admin', '2023-09-16 14:22:35', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10039, 7, 'default.jpg', '怎么出现了', '不写id的下场', '0', '0', 100000, 'admin', '2023-09-16 14:22:37', '2023-11-12 10:45:22');
INSERT INTO `blog_category` VALUES (10040, 5, 'cover_20230915024222000000289.jpg', '我就试试', 'asd', '1', '1', 100000, 'admin', '2023-09-17 14:38:09', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10042, 6, 'cover_20230917033044000000218.jpg', '你不是aaa吗', '我觉得我是', '0', '1', 100000, 'admin', '2023-09-17 15:29:39', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10054, 9, 'default.jpg', '我的天哪', '添加了header', '0', '1', 100000, 'admin', '2023-10-29 14:22:13', '2023-11-12 10:57:10');
INSERT INTO `blog_category` VALUES (10055, 10, 'cover_20231111043146000000936.jpg', '试试草稿删除', '马上给你删了', '1', '1', 100002, 'dz', '2023-11-11 16:31:21', '2023-11-12 10:45:22');
INSERT INTO `blog_category` VALUES (10056, 11, 'default.jpg', '看看need', 'qwe', '1', '0', 100002, 'dz', '2023-11-12 15:25:17', '2023-11-12 15:54:50');

-- ----------------------------
-- Table structure for blog_city
-- ----------------------------
DROP TABLE IF EXISTS `blog_city`;
CREATE TABLE `blog_city`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `area` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '东北' COMMENT '所属区域',
  `city` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '城市名称',
  `value` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '对应编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_city
-- ----------------------------
INSERT INTO `blog_city` VALUES (21, '华北', '北京', '54511');
INSERT INTO `blog_city` VALUES (22, '华北', '呼和浩特', '53463');
INSERT INTO `blog_city` VALUES (23, '华北', '太原', '53772');
INSERT INTO `blog_city` VALUES (24, '华北', '石家庄', '53698');
INSERT INTO `blog_city` VALUES (25, '华北', '天津', '54517');
INSERT INTO `blog_city` VALUES (26, '华北', '张家口', '54401');
INSERT INTO `blog_city` VALUES (27, '华北', '任丘', '54610');
INSERT INTO `blog_city` VALUES (28, '华北', '保定', '54602');
INSERT INTO `blog_city` VALUES (29, '华北', '唐山', '54534');
INSERT INTO `blog_city` VALUES (30, '华北', '廊坊', '54515');
INSERT INTO `blog_city` VALUES (31, '华北', '承德', '54423');
INSERT INTO `blog_city` VALUES (32, '华北', '沧州', '54616');
INSERT INTO `blog_city` VALUES (33, '华北', '秦皇岛', '54449');
INSERT INTO `blog_city` VALUES (34, '华北', '衡水', '54702');
INSERT INTO `blog_city` VALUES (35, '华北', '邢台', '53798');
INSERT INTO `blog_city` VALUES (36, '华北', '邯郸', '53892');
INSERT INTO `blog_city` VALUES (37, '华北', '临汾', '53868');
INSERT INTO `blog_city` VALUES (38, '华北', '五台山', '53588');
INSERT INTO `blog_city` VALUES (39, '华北', '吕梁', '53764');
INSERT INTO `blog_city` VALUES (40, '华北', '大同', '53487');
INSERT INTO `blog_city` VALUES (41, '华北', '平遥', '53778');
INSERT INTO `blog_city` VALUES (42, '华北', '忻州', '53674');
INSERT INTO `blog_city` VALUES (43, '华北', '文水', '53771');
INSERT INTO `blog_city` VALUES (44, '华北', '晋城', '53976');
INSERT INTO `blog_city` VALUES (45, '华北', '朔州', '53578');
INSERT INTO `blog_city` VALUES (46, '华北', '运城', '53959');
INSERT INTO `blog_city` VALUES (47, '华北', '长治', '53882');
INSERT INTO `blog_city` VALUES (48, '华北', '阳泉', '53782');
INSERT INTO `blog_city` VALUES (49, '华北', '黎城', '53878');
INSERT INTO `blog_city` VALUES (50, '华北', '东胜', '53543');
INSERT INTO `blog_city` VALUES (51, '华北', '临河', '53513');
INSERT INTO `blog_city` VALUES (52, '华北', '乌兰浩特', '50838');
INSERT INTO `blog_city` VALUES (53, '华北', '乌海', '53512');
INSERT INTO `blog_city` VALUES (54, '华北', '二连浩特', '53068');
INSERT INTO `blog_city` VALUES (55, '华北', '包头', '53446');
INSERT INTO `blog_city` VALUES (56, '华北', '巴林右旗', '54113');
INSERT INTO `blog_city` VALUES (57, '华北', '海拉尔', '50527');
INSERT INTO `blog_city` VALUES (58, '华北', '赤峰', '54218');
INSERT INTO `blog_city` VALUES (59, '华北', '通辽', '54135');
INSERT INTO `blog_city` VALUES (60, '华北', '锡林浩特', '54102');
INSERT INTO `blog_city` VALUES (61, '华北', '阿拉善右旗', '52576');
INSERT INTO `blog_city` VALUES (62, '华北', '阿拉善左旗', '53602');
INSERT INTO `blog_city` VALUES (63, '华北', '集宁', '53480');
INSERT INTO `blog_city` VALUES (64, '东北', '哈尔滨', '50953');
INSERT INTO `blog_city` VALUES (65, '东北', '沈阳', '54342');
INSERT INTO `blog_city` VALUES (66, '东北', '长春', '54161');
INSERT INTO `blog_city` VALUES (67, '东北', '吉林', '54172');
INSERT INTO `blog_city` VALUES (68, '东北', '四平', '54157');
INSERT INTO `blog_city` VALUES (69, '东北', '延吉', '54292');
INSERT INTO `blog_city` VALUES (70, '东北', '松原', '50946');
INSERT INTO `blog_city` VALUES (71, '东北', '桦甸', '54273');
INSERT INTO `blog_city` VALUES (72, '东北', '梅河口', '54266');
INSERT INTO `blog_city` VALUES (73, '东北', '珲春', '54291');
INSERT INTO `blog_city` VALUES (74, '东北', '白城', '50936');
INSERT INTO `blog_city` VALUES (75, '东北', '白山', '54371');
INSERT INTO `blog_city` VALUES (76, '东北', '辽源', '54260');
INSERT INTO `blog_city` VALUES (77, '东北', '通化', '54363');
INSERT INTO `blog_city` VALUES (78, '东北', '集安', '54377');
INSERT INTO `blog_city` VALUES (79, '东北', '七台河', '50971');
INSERT INTO `blog_city` VALUES (80, '东北', '伊春', '50774');
INSERT INTO `blog_city` VALUES (81, '东北', '佳木斯', '50873');
INSERT INTO `blog_city` VALUES (82, '东北', '加格达奇', '50442');
INSERT INTO `blog_city` VALUES (83, '东北', '勃利', '50973');
INSERT INTO `blog_city` VALUES (84, '东北', '双鸭山', '50884');
INSERT INTO `blog_city` VALUES (85, '东北', '大庆', '50850');
INSERT INTO `blog_city` VALUES (86, '东北', '杜蒙', '50842');
INSERT INTO `blog_city` VALUES (87, '东北', '漠河', '50136');
INSERT INTO `blog_city` VALUES (88, '东北', '牡丹江', '54094');
INSERT INTO `blog_city` VALUES (89, '东北', '甘南', '50741');
INSERT INTO `blog_city` VALUES (90, '东北', '绥化', '50853');
INSERT INTO `blog_city` VALUES (91, '东北', '绥芬河', '54096');
INSERT INTO `blog_city` VALUES (92, '东北', '鸡西', '50978');
INSERT INTO `blog_city` VALUES (93, '东北', '鹤岗', '50775');
INSERT INTO `blog_city` VALUES (94, '东北', '黑河', '50468');
INSERT INTO `blog_city` VALUES (95, '东北', '齐齐哈尔', '50745');
INSERT INTO `blog_city` VALUES (96, '东北', '丹东', '54497');
INSERT INTO `blog_city` VALUES (97, '东北', '大连', '54662');
INSERT INTO `blog_city` VALUES (98, '东北', '新宾', '54353');
INSERT INTO `blog_city` VALUES (99, '东北', '朝阳', '54324');
INSERT INTO `blog_city` VALUES (100, '东北', '本溪', '54346');
INSERT INTO `blog_city` VALUES (101, '东北', '瓦房店', '54563');
INSERT INTO `blog_city` VALUES (102, '东北', '营口', '54471');
INSERT INTO `blog_city` VALUES (103, '东北', '葫芦岛', '54453');
INSERT INTO `blog_city` VALUES (104, '东北', '辽阳', '54347');
INSERT INTO `blog_city` VALUES (105, '东北', '铁岭', '54249');
INSERT INTO `blog_city` VALUES (106, '东北', '锦州', '54337');
INSERT INTO `blog_city` VALUES (107, '东北', '阜新', '54237');
INSERT INTO `blog_city` VALUES (108, '东北', '鞍山', '54339');
INSERT INTO `blog_city` VALUES (109, '东北', '盘山', '54338');
INSERT INTO `blog_city` VALUES (110, '华东', '上海', '58367');
INSERT INTO `blog_city` VALUES (111, '华东', '南京', '58238');
INSERT INTO `blog_city` VALUES (112, '华东', '南昌', '58606');
INSERT INTO `blog_city` VALUES (113, '华东', '合肥', '58321');
INSERT INTO `blog_city` VALUES (114, '华东', '杭州', '58457');
INSERT INTO `blog_city` VALUES (115, '华东', '济南', '54823');
INSERT INTO `blog_city` VALUES (116, '华东', '福州', '58847');
INSERT INTO `blog_city` VALUES (117, '华东', '东营', '54736');
INSERT INTO `blog_city` VALUES (118, '华东', '临沂', '54938');
INSERT INTO `blog_city` VALUES (119, '华东', '威海', '54774');
INSERT INTO `blog_city` VALUES (120, '华东', '德州', '54714');
INSERT INTO `blog_city` VALUES (121, '华东', '日照', '54945');
INSERT INTO `blog_city` VALUES (122, '华东', '枣庄', '58024');
INSERT INTO `blog_city` VALUES (123, '华东', '泰安', '54827');
INSERT INTO `blog_city` VALUES (124, '华东', '济宁', '54915');
INSERT INTO `blog_city` VALUES (125, '华东', '淄博', '54830');
INSERT INTO `blog_city` VALUES (126, '华东', '滨州', '54734');
INSERT INTO `blog_city` VALUES (127, '华东', '潍坊', '54843');
INSERT INTO `blog_city` VALUES (128, '华东', '烟台', '54765');
INSERT INTO `blog_city` VALUES (129, '华东', '聊城', '54806');
INSERT INTO `blog_city` VALUES (130, '华东', '莱芜', '54828');
INSERT INTO `blog_city` VALUES (131, '华东', '菏泽', '54906');
INSERT INTO `blog_city` VALUES (132, '华东', '青岛', '54857');
INSERT INTO `blog_city` VALUES (133, '华东', '亳州', '58102');
INSERT INTO `blog_city` VALUES (134, '华东', '六安', '58311');
INSERT INTO `blog_city` VALUES (135, '华东', '安庆', '58424');
INSERT INTO `blog_city` VALUES (136, '华东', '宣城', '58433');
INSERT INTO `blog_city` VALUES (137, '华东', '宿州', '58122');
INSERT INTO `blog_city` VALUES (138, '华东', '巢湖', '58326');
INSERT INTO `blog_city` VALUES (139, '华东', '池州', '58427');
INSERT INTO `blog_city` VALUES (140, '华东', '淮北', '58116');
INSERT INTO `blog_city` VALUES (141, '华东', '淮南', '58224');
INSERT INTO `blog_city` VALUES (142, '华东', '滁州', '58236');
INSERT INTO `blog_city` VALUES (143, '华东', '芜湖', '58334');
INSERT INTO `blog_city` VALUES (144, '华东', '蚌埠', '58221');
INSERT INTO `blog_city` VALUES (145, '华东', '铜陵', '58429');
INSERT INTO `blog_city` VALUES (146, '华东', '阜阳', '58203');
INSERT INTO `blog_city` VALUES (147, '华东', '马鞍山', '58336');
INSERT INTO `blog_city` VALUES (148, '华东', '黄山市', '58531');
INSERT INTO `blog_city` VALUES (149, '华东', '东台', '58251');
INSERT INTO `blog_city` VALUES (150, '华东', '南通', '58259');
INSERT INTO `blog_city` VALUES (151, '华东', '宿迁', '58131');
INSERT INTO `blog_city` VALUES (152, '华东', '常州', '58343');
INSERT INTO `blog_city` VALUES (153, '华东', '徐州', '58027');
INSERT INTO `blog_city` VALUES (154, '华东', '扬州', '58245');
INSERT INTO `blog_city` VALUES (155, '华东', '无锡', '58354');
INSERT INTO `blog_city` VALUES (156, '华东', '楚州', '58145');
INSERT INTO `blog_city` VALUES (157, '华东', '泰州', '58246');
INSERT INTO `blog_city` VALUES (158, '华中', '武汉', '57494');
INSERT INTO `blog_city` VALUES (159, '华中', '郑州', '57083');
INSERT INTO `blog_city` VALUES (160, '华中', '长沙', '57679');
INSERT INTO `blog_city` VALUES (161, '华中', '三门峡', '57051');
INSERT INTO `blog_city` VALUES (162, '华中', '信阳市', '57297');
INSERT INTO `blog_city` VALUES (163, '华中', '南阳市', '57178');
INSERT INTO `blog_city` VALUES (164, '华中', '周口', '57195');
INSERT INTO `blog_city` VALUES (165, '华中', '商丘', '58005');
INSERT INTO `blog_city` VALUES (166, '华中', '安阳市', '53898');
INSERT INTO `blog_city` VALUES (167, '华中', '平顶山', '57171');
INSERT INTO `blog_city` VALUES (168, '华中', '开封市', '57091');
INSERT INTO `blog_city` VALUES (169, '华中', '新乡市', '53986');
INSERT INTO `blog_city` VALUES (170, '华中', '洛阳市', '57073');
INSERT INTO `blog_city` VALUES (171, '华中', '济源', '53978');
INSERT INTO `blog_city` VALUES (172, '华中', '漯河市', '57186');
INSERT INTO `blog_city` VALUES (173, '华中', '潢川', '58207');
INSERT INTO `blog_city` VALUES (174, '华中', '濮阳', '54900');
INSERT INTO `blog_city` VALUES (175, '华中', '焦作市', '53982');
INSERT INTO `blog_city` VALUES (176, '华中', '许昌', '57089');
INSERT INTO `blog_city` VALUES (177, '华中', '驻马店市', '57290');
INSERT INTO `blog_city` VALUES (178, '华中', '鹤壁', '53990');
INSERT INTO `blog_city` VALUES (179, '华中', '仙桃', '57485');
INSERT INTO `blog_city` VALUES (180, '华中', '十堰', '57256');
INSERT INTO `blog_city` VALUES (181, '华中', '咸宁', '57590');
INSERT INTO `blog_city` VALUES (182, '华中', '天门', '57483');
INSERT INTO `blog_city` VALUES (183, '华中', '孝感', '57482');
INSERT INTO `blog_city` VALUES (184, '华中', '宜城', '57370');
INSERT INTO `blog_city` VALUES (185, '华中', '宜昌', '57461');
INSERT INTO `blog_city` VALUES (186, '华中', '恩施', '57447');
INSERT INTO `blog_city` VALUES (187, '华中', '枣阳', '57279');
INSERT INTO `blog_city` VALUES (188, '华中', '潜江', '57475');
INSERT INTO `blog_city` VALUES (189, '华中', '神农架', '57362');
INSERT INTO `blog_city` VALUES (190, '华中', '荆州', '57476');
INSERT INTO `blog_city` VALUES (191, '华中', '荆门', '57377');
INSERT INTO `blog_city` VALUES (192, '华中', '襄阳', '57278');
INSERT INTO `blog_city` VALUES (193, '华中', '鄂州', '57496');
INSERT INTO `blog_city` VALUES (194, '华中', '随州', '57381');
INSERT INTO `blog_city` VALUES (195, '华中', '麻城', '57399');
INSERT INTO `blog_city` VALUES (196, '华中', '黄冈', '57498');
INSERT INTO `blog_city` VALUES (197, '华中', '黄石', '58407');
INSERT INTO `blog_city` VALUES (198, '华中', '临武', '57978');
INSERT INTO `blog_city` VALUES (199, '华中', '南岳', '57776');
INSERT INTO `blog_city` VALUES (200, '华中', '吉首', '57649');
INSERT INTO `blog_city` VALUES (201, '华中', '娄底', '57763');
INSERT INTO `blog_city` VALUES (202, '华中', '岳阳', '57584');
INSERT INTO `blog_city` VALUES (203, '华中', '常德', '57662');
INSERT INTO `blog_city` VALUES (204, '华中', '张家界', '57558');
INSERT INTO `blog_city` VALUES (205, '华中', '怀化', '57749');
INSERT INTO `blog_city` VALUES (206, '华南', '广州', '59287');
INSERT INTO `blog_city` VALUES (207, '华南', '海口', '59758');
INSERT INTO `blog_city` VALUES (208, '华南', '深圳', '59493');
INSERT INTO `blog_city` VALUES (209, '华南', '东莞', '59289');
INSERT INTO `blog_city` VALUES (210, '华南', '中山', '59485');
INSERT INTO `blog_city` VALUES (211, '华南', '云浮', '59471');
INSERT INTO `blog_city` VALUES (212, '华南', '佛冈', '59087');
INSERT INTO `blog_city` VALUES (213, '华南', '佛山', '59288');
INSERT INTO `blog_city` VALUES (214, '华南', '南雄', '57996');
INSERT INTO `blog_city` VALUES (215, '华南', '惠州', '59298');
INSERT INTO `blog_city` VALUES (216, '华南', '揭阳', '59315');
INSERT INTO `blog_city` VALUES (217, '华南', '梅县', '59117');
INSERT INTO `blog_city` VALUES (218, '华南', '梅州', '591170');
INSERT INTO `blog_city` VALUES (219, '华南', '汕头', '59316');
INSERT INTO `blog_city` VALUES (220, '华南', '汕尾', '59501');
INSERT INTO `blog_city` VALUES (221, '华南', '河源', '59293');
INSERT INTO `blog_city` VALUES (222, '华南', '清远', '59280');
INSERT INTO `blog_city` VALUES (223, '华南', '湛江', '59658');
INSERT INTO `blog_city` VALUES (224, '华南', '潮州', '59312');
INSERT INTO `blog_city` VALUES (225, '华南', '珠海', '59488');
INSERT INTO `blog_city` VALUES (226, '华南', '电白', '59664');
INSERT INTO `blog_city` VALUES (227, '华南', '肇庆', '592780');
INSERT INTO `blog_city` VALUES (228, '华南', '茂名', '59659');
INSERT INTO `blog_city` VALUES (229, '华南', '阳江', '59663');
INSERT INTO `blog_city` VALUES (230, '华南', '韶关', '59082');
INSERT INTO `blog_city` VALUES (231, '华南', '顺德', '59480');
INSERT INTO `blog_city` VALUES (232, '华南', '饶平', '59313');
INSERT INTO `blog_city` VALUES (233, '华南', '高要', '59278');
INSERT INTO `blog_city` VALUES (234, '华南', '鹤山', '59473');
INSERT INTO `blog_city` VALUES (235, '华南', '万宁', '59951');
INSERT INTO `blog_city` VALUES (236, '华南', '三亚', '59948');
INSERT INTO `blog_city` VALUES (237, '华南', '东方', '59838');
INSERT INTO `blog_city` VALUES (238, '华南', '临高', '59842');
INSERT INTO `blog_city` VALUES (239, '华南', '乐东', '59940');
INSERT INTO `blog_city` VALUES (240, '华南', '保亭', '59945');
INSERT INTO `blog_city` VALUES (241, '华南', '儋州', '59845');
INSERT INTO `blog_city` VALUES (242, '华南', '定安', '59851');
INSERT INTO `blog_city` VALUES (243, '华南', '屯昌', '59854');
INSERT INTO `blog_city` VALUES (244, '华南', '文昌', '59856');
INSERT INTO `blog_city` VALUES (245, '华南', '澄迈', '59843');
INSERT INTO `blog_city` VALUES (246, '华南', '琼中', '59849');
INSERT INTO `blog_city` VALUES (247, '华南', '琼海', '59855');
INSERT INTO `blog_city` VALUES (248, '华南', '白沙', '59848');
INSERT INTO `blog_city` VALUES (249, '华南', '陵水', '59954');
INSERT INTO `blog_city` VALUES (250, '华南', '北海', '59644');
INSERT INTO `blog_city` VALUES (251, '华南', '崇左', '59425');
INSERT INTO `blog_city` VALUES (252, '华南', '来宾', '59242');
INSERT INTO `blog_city` VALUES (253, '华南', '柳州', '59046');
INSERT INTO `blog_city` VALUES (254, '西北', '乌鲁木齐', '51463');
INSERT INTO `blog_city` VALUES (255, '西北', '兰州', '52889');
INSERT INTO `blog_city` VALUES (256, '西北', '西宁', '52866');
INSERT INTO `blog_city` VALUES (257, '西北', '西安', '57036');
INSERT INTO `blog_city` VALUES (258, '西北', '银川', '53614');
INSERT INTO `blog_city` VALUES (259, '西北', '咸阳', '57048');
INSERT INTO `blog_city` VALUES (260, '西北', '商洛', '57143');
INSERT INTO `blog_city` VALUES (261, '西北', '安康', '57245');
INSERT INTO `blog_city` VALUES (262, '西北', '宝鸡', '57016');
INSERT INTO `blog_city` VALUES (263, '西北', '延安', '53845');
INSERT INTO `blog_city` VALUES (264, '西北', '榆林', '53646');
INSERT INTO `blog_city` VALUES (265, '西北', '汉中', '57127');
INSERT INTO `blog_city` VALUES (266, '西北', '渭南', '57045');
INSERT INTO `blog_city` VALUES (267, '西北', '铜川', '53947');
INSERT INTO `blog_city` VALUES (268, '西北', '临夏', '52984');
INSERT INTO `blog_city` VALUES (269, '西北', '嘉峪关', '52532');
INSERT INTO `blog_city` VALUES (270, '西北', '天水', '57006');
INSERT INTO `blog_city` VALUES (271, '西北', '安定', '52995');
INSERT INTO `blog_city` VALUES (272, '西北', '崆峒', '53915');
INSERT INTO `blog_city` VALUES (273, '西北', '庆城', '53829');
INSERT INTO `blog_city` VALUES (274, '西北', '张掖', '52652');
INSERT INTO `blog_city` VALUES (275, '西北', '武威', '52679');
INSERT INTO `blog_city` VALUES (276, '西北', '武都', '56096');
INSERT INTO `blog_city` VALUES (277, '西北', '白银', '52896');
INSERT INTO `blog_city` VALUES (278, '西北', '酒泉', '52533');
INSERT INTO `blog_city` VALUES (279, '西北', '共和', '52856');
INSERT INTO `blog_city` VALUES (280, '西北', '刚察', '52754');
INSERT INTO `blog_city` VALUES (281, '西北', '平安', '52875');
INSERT INTO `blog_city` VALUES (282, '西北', '德令哈', '52737');
INSERT INTO `blog_city` VALUES (283, '西北', '格尔木', '52818');
INSERT INTO `blog_city` VALUES (284, '西北', '河南', '56065');
INSERT INTO `blog_city` VALUES (285, '西北', '海晏', '52853');
INSERT INTO `blog_city` VALUES (286, '西北', '玉树', '56029');
INSERT INTO `blog_city` VALUES (287, '西北', '玛沁', '56043');
INSERT INTO `blog_city` VALUES (288, '西北', '伊宁市', '51431');
INSERT INTO `blog_city` VALUES (289, '西北', '克拉玛依', '51243');
INSERT INTO `blog_city` VALUES (290, '西北', '博乐', '51238');
INSERT INTO `blog_city` VALUES (291, '西北', '吐鲁番', '51573');
INSERT INTO `blog_city` VALUES (292, '西北', '和田', '51828');
INSERT INTO `blog_city` VALUES (293, '西北', '哈密', '52203');
INSERT INTO `blog_city` VALUES (294, '西北', '喀什', '51709');
INSERT INTO `blog_city` VALUES (295, '西北', '塔城', '51133');
INSERT INTO `blog_city` VALUES (296, '西北', '奇台', '51379');
INSERT INTO `blog_city` VALUES (297, '西北', '库尔勒', '51656');
INSERT INTO `blog_city` VALUES (298, '西北', '昌吉', '51368');
INSERT INTO `blog_city` VALUES (299, '西北', '沙湾', '51357');
INSERT INTO `blog_city` VALUES (300, '西北', '石河子', '51356');
INSERT INTO `blog_city` VALUES (301, '西北', '阿克苏', '51628');
INSERT INTO `blog_city` VALUES (302, '西南', '成都', '56294');
INSERT INTO `blog_city` VALUES (303, '西南', '拉萨', '55591');
INSERT INTO `blog_city` VALUES (304, '西南', '昆明', '56778');
INSERT INTO `blog_city` VALUES (305, '西南', '贵阳', '57816');
INSERT INTO `blog_city` VALUES (306, '西南', '重庆', '57516');
INSERT INTO `blog_city` VALUES (307, '西南', '奉节', '57348');
INSERT INTO `blog_city` VALUES (308, '西南', '涪陵', '57522');
INSERT INTO `blog_city` VALUES (309, '西南', '黔江', '57536');
INSERT INTO `blog_city` VALUES (310, '西南', '乐山', '56386');
INSERT INTO `blog_city` VALUES (311, '西南', '内江', '57503');
INSERT INTO `blog_city` VALUES (312, '西南', '南充', '57411');
INSERT INTO `blog_city` VALUES (313, '西南', '宜宾', '56492');
INSERT INTO `blog_city` VALUES (314, '西南', '峨眉山', '56385');
INSERT INTO `blog_city` VALUES (315, '西南', '巴中', '57313');
INSERT INTO `blog_city` VALUES (316, '西南', '广元', '57206');
INSERT INTO `blog_city` VALUES (317, '西南', '广安', '57415');
INSERT INTO `blog_city` VALUES (318, '西南', '德阳', '56198');
INSERT INTO `blog_city` VALUES (319, '西南', '攀枝花', '56666');
INSERT INTO `blog_city` VALUES (320, '西南', '泸县', '57508');
INSERT INTO `blog_city` VALUES (321, '西南', '泸州', '57602');
INSERT INTO `blog_city` VALUES (322, '西南', '甘孜', '56146');
INSERT INTO `blog_city` VALUES (323, '西南', '眉山', '56391');
INSERT INTO `blog_city` VALUES (324, '西南', '绵阳', '56196');
INSERT INTO `blog_city` VALUES (325, '西南', '自贡', '56396');
INSERT INTO `blog_city` VALUES (326, '西南', '西昌', '56571');
INSERT INTO `blog_city` VALUES (327, '西南', '资阳', '56298');
INSERT INTO `blog_city` VALUES (328, '西南', '达州', '57328');
INSERT INTO `blog_city` VALUES (329, '西南', '遂宁', '57405');
INSERT INTO `blog_city` VALUES (330, '西南', '阿坝', '56171');
INSERT INTO `blog_city` VALUES (331, '西南', '雅安', '56287');
INSERT INTO `blog_city` VALUES (332, '西南', '临沧', '56951');
INSERT INTO `blog_city` VALUES (333, '西南', '丽江', '56651');
INSERT INTO `blog_city` VALUES (334, '西南', '保山', '56748');
INSERT INTO `blog_city` VALUES (335, '西南', '大理', '56751');
INSERT INTO `blog_city` VALUES (336, '西南', '思茅', '56964');
INSERT INTO `blog_city` VALUES (337, '西南', '文山', '56994');
INSERT INTO `blog_city` VALUES (338, '西南', '昭通', '56586');
INSERT INTO `blog_city` VALUES (339, '西南', '景洪', '56959');
INSERT INTO `blog_city` VALUES (340, '西南', '楚雄', '56768');
INSERT INTO `blog_city` VALUES (341, '西南', '潞西', '56844');
INSERT INTO `blog_city` VALUES (342, '西南', '玉溪', '56875');
INSERT INTO `blog_city` VALUES (343, '西南', '瑞丽', '56838');
INSERT INTO `blog_city` VALUES (344, '西南', '红河', '56975');
INSERT INTO `blog_city` VALUES (345, '西南', '贡山', '56533');
INSERT INTO `blog_city` VALUES (346, '西南', '香格里拉', '56543');
INSERT INTO `blog_city` VALUES (347, '西南', '兴义', '57907');
INSERT INTO `blog_city` VALUES (348, '西南', '凯里', '57825');
INSERT INTO `blog_city` VALUES (349, '西南', '安顺', '57806');
INSERT INTO `blog_city` VALUES (350, '港澳台', '香港', '45005');
INSERT INTO `blog_city` VALUES (351, '港澳台', '澳门', '45011');
INSERT INTO `blog_city` VALUES (352, '港澳台', '台北', '58968');
INSERT INTO `blog_city` VALUES (353, '港澳台', '台东', '59562');
INSERT INTO `blog_city` VALUES (354, '港澳台', '台中', '59158');
INSERT INTO `blog_city` VALUES (355, '港澳台', '台南', '59358');
INSERT INTO `blog_city` VALUES (356, '港澳台', '嘉义', '59354');
INSERT INTO `blog_city` VALUES (357, '港澳台', '基隆', '58964');
INSERT INTO `blog_city` VALUES (358, '港澳台', '宜兰', '59162');
INSERT INTO `blog_city` VALUES (359, '港澳台', '新竹', '59152');
INSERT INTO `blog_city` VALUES (360, '港澳台', '桃园', '58965');
INSERT INTO `blog_city` VALUES (361, '港澳台', '花莲', '59362');
INSERT INTO `blog_city` VALUES (362, '港澳台', '高雄', '59554');

-- ----------------------------
-- Table structure for blog_model
-- ----------------------------
DROP TABLE IF EXISTS `blog_model`;
CREATE TABLE `blog_model`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '模板的id',
  `model_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '模板的标题',
  `cssLeft` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '模板css结构的左边部分',
  `cssRight` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '模板css结构的右边部分',
  `create_userid` int(0) NULL DEFAULT NULL COMMENT '创建模板的作者id',
  `type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '模板的类型（0为仅自己使用，1为共享）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_model
-- ----------------------------
INSERT INTO `blog_model` VALUES (1, 'h1标题', 'h1 style=\"text-align: center;\"', 'h1', 100000, '1');
INSERT INTO `blog_model` VALUES (2, 'h3标题', 'h3 style=\"text-align: center;\"', 'h3', 100000, '1');
INSERT INTO `blog_model` VALUES (3, '天蓝色正文', 'p style=\"color: aqua;text-indent: 2em;\"', 'p', 100002, '1');
INSERT INTO `blog_model` VALUES (4, '红色正文', 'p style=\"color: red;text-indent: 2em;\"', 'p', 100000, '1');
INSERT INTO `blog_model` VALUES (5, '普通黑色正文', 'p style=\"text-indent: 2em;\"', 'p', 100002, '1');
INSERT INTO `blog_model` VALUES (7, '修改后添加模板', 'p', 'p', 100000, '1');
INSERT INTO `blog_model` VALUES (8, '试试补全后的模板', 'p', 'p', 100000, '1');
INSERT INTO `blog_model` VALUES (9, '上传图片', 'div style=\"width: 200px;height: 200px;margin: 0 auto;\"', '></div', 100000, '1');

-- ----------------------------
-- Table structure for blog_sort
-- ----------------------------
DROP TABLE IF EXISTS `blog_sort`;
CREATE TABLE `blog_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL COMMENT '用户的id',
  `category_id` int(0) NULL DEFAULT NULL COMMENT '博客id',
  `sort` int(0) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_sort
-- ----------------------------
INSERT INTO `blog_sort` VALUES (31, 100004, 10027, 2);
INSERT INTO `blog_sort` VALUES (32, 100004, 10031, 3);
INSERT INTO `blog_sort` VALUES (33, 100004, 10035, 4);
INSERT INTO `blog_sort` VALUES (34, 100004, 10040, 5);
INSERT INTO `blog_sort` VALUES (35, 100004, 10054, 4);
INSERT INTO `blog_sort` VALUES (44, 100002, 10027, 2);
INSERT INTO `blog_sort` VALUES (45, 100002, 10031, 3);
INSERT INTO `blog_sort` VALUES (46, 100002, 10035, 4);
INSERT INTO `blog_sort` VALUES (47, 100002, 10040, 5);
INSERT INTO `blog_sort` VALUES (48, 100002, 10055, 1);
INSERT INTO `blog_sort` VALUES (49, 100000, 10031, 3);
INSERT INTO `blog_sort` VALUES (50, 100000, 10034, 1);
INSERT INTO `blog_sort` VALUES (51, 100000, 10035, 4);
INSERT INTO `blog_sort` VALUES (52, 100000, 10040, 5);
INSERT INTO `blog_sort` VALUES (53, 100000, 10055, 1);
INSERT INTO `blog_sort` VALUES (54, 100002, 10056, 0);

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default.jpg' COMMENT '头像',
  `tname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'user' COMMENT '昵称',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值（用于加密）',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '0:启用 1:禁用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客成员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_user
-- ----------------------------
INSERT INTO `blog_user` VALUES (100000, 'admin', 'avatar_20231109022755000000208.jpg', 'admin', '74DDB8E5-A3FE-403D-8F2A-2904904BC180', '0BD446F211136F78CD55E6A23C30CBDE', '0', '2021-12-04 20:56:01', '2023-11-28 13:01:21');
INSERT INTO `blog_user` VALUES (100002, 'dz', 'default.jpg', '芝士雪豹', '21526CB7-E2C2-445E-84F8-983CB97FE15B', 'B8AD590122B68742F6B67C23123EE6A8', '0', '2022-09-24 18:35:14', '2023-11-12 15:17:24');
INSERT INTO `blog_user` VALUES (100003, 'zx123', 'default.jpg', 'zx', '63A74E50-A04C-47EF-8F26-080BFFEC7AA2', '8A5308085B0AED1310C8F30BE75296E6', '1', '2023-11-04 09:28:58', NULL);
INSERT INTO `blog_user` VALUES (100004, 'test', 'avatar_20231109030113000000464.jpg', 'zxc', '74DDB8E5-A3FE-403D-8F2A-2904904BC180', '0BD446F211136F78CD55E6A23C30CBDE', '0', '2023-11-04 13:56:32', '2023-11-10 14:10:28');
INSERT INTO `blog_user` VALUES (100005, 'test2', 'default.jpg', 'user', '21526CB7-E2C2-445E-84F8-983CB97FE15B', 'B8AD590122B68742F6B67C23123EE6A8', '1', '2023-11-04 13:57:28', NULL);

-- ----------------------------
-- Table structure for category_model
-- ----------------------------
DROP TABLE IF EXISTS `category_model`;
CREATE TABLE `category_model`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '文章和模板的链接id',
  `category_id` int(0) NULL DEFAULT NULL COMMENT '文章id',
  `model_id` int(0) NULL DEFAULT NULL COMMENT '模板id',
  `model_sort` int(0) NULL DEFAULT NULL COMMENT '模板的顺序',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '模板输入内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_model
-- ----------------------------
INSERT INTO `category_model` VALUES (1, 1001, 3, 0, '哈哈');
INSERT INTO `category_model` VALUES (2, 1001, 1, 1, '12453453');
INSERT INTO `category_model` VALUES (3, 1001, 4, 2, '顶真');
INSERT INTO `category_model` VALUES (8, 10032, 1, 0, '这是几个？');
INSERT INTO `category_model` VALUES (9, 10032, 2, 1, '再来一个');
INSERT INTO `category_model` VALUES (10, 10032, 1, 2, '马上删除');
INSERT INTO `category_model` VALUES (11, 10032, 4, 3, '改下顺序');
INSERT INTO `category_model` VALUES (37, 10031, 1, 0, '真的好玩');
INSERT INTO `category_model` VALUES (38, 10031, 5, 1, '哈哈哈');
INSERT INTO `category_model` VALUES (39, 10031, 5, 2, '看看need，会不会变长');
INSERT INTO `category_model` VALUES (40, 10031, 9, 3, '<img style=\"height: 100%;width: 100%;\" src=\"http://localhost:8126/images/content/20230922093923000000159.jpg\"');
INSERT INTO `category_model` VALUES (41, 10031, 4, 4, '下面试试添加');
INSERT INTO `category_model` VALUES (42, 10054, 1, 0, '测试测试');
INSERT INTO `category_model` VALUES (43, 10054, 5, 1, 'zasx');
INSERT INTO `category_model` VALUES (44, 10054, 5, 2, '马上给你删了');
INSERT INTO `category_model` VALUES (45, 10054, 9, 3, '<img style=\"height: 100%;width: 100%;\" src=\"http://localhost:8126/images/content/20231029030720000000070.jpg\"');
INSERT INTO `category_model` VALUES (46, 10055, 1, 0, '芝士芝士雪豹');
INSERT INTO `category_model` VALUES (47, 10055, 5, 1, '我去吃我闪焰冲锋');
INSERT INTO `category_model` VALUES (48, 10056, 1, 0, '哈哈');
INSERT INTO `category_model` VALUES (49, 10056, 9, 1, '<img style=\"height: 100%;width: 100%;\" src=\"http://localhost:8126/images/content/20231112041150000000670.jpg\"');

SET FOREIGN_KEY_CHECKS = 1;
