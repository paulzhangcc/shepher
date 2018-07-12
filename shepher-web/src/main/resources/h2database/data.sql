INSERT INTO `cluster` VALUES (1,'LOCAL','127.0.0.1:2181',CURRENT_DATE);
INSERT INTO `cluster` VALUES (2,'TEST','10.143.36.61:7004',CURRENT_DATE);

INSERT INTO `team` VALUES (1,'admin',1,CURRENT_DATE);
INSERT INTO `user` VALUES (1,'admin',CURRENT_DATE,'123456');
INSERT INTO `user_team` VALUES (1,1,1,100,10,CURRENT_DATE);
INSERT INTO `permission` VALUES (1,'LOCAL','/',CURRENT_DATE);
INSERT INTO `permission` VALUES (2,'TEST','/',CURRENT_DATE);
INSERT INTO `permission_team` VALUES (1,1,1,10,CURRENT_DATE);
INSERT INTO `permission_team` VALUES (2,2,1,10,CURRENT_DATE);


