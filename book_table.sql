CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `name` varchar(100) NOT NULL COMMENT '图书名称',
  `isbn` varchar(50) DEFAULT NULL COMMENT '图书标准ISBN编号',
  `press` varchar(100) DEFAULT NULL COMMENT '图书出版社',
  `author` varchar(50) DEFAULT NULL COMMENT '图书作者',
  `pagination` int DEFAULT NULL COMMENT '图书页数',
  `price` double(10,2) DEFAULT NULL COMMENT '图书价格',
  `upload_time` datetime DEFAULT NULL COMMENT '图书上架时间',
  `status` varchar(10) DEFAULT NULL COMMENT '图书状态（0：可借阅 1：借阅中 2：归还中）',
  `borrower` varchar(50) DEFAULT NULL COMMENT '图书借阅人',
  `borrow_time` datetime DEFAULT NULL COMMENT '图书借阅时间',
  `return_time` datetime DEFAULT NULL COMMENT '图书预计归还时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;