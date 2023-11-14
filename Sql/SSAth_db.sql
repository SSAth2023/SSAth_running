CREATE DATABASE IF NOT EXISTS SSAth_DB;
USE SSAth_DB;

CREATE TABLE IF NOT EXISTS User (   
	user_id VARCHAR(20) PRIMARY KEY,   
    name VARCHAR(8),   
    password VARCHAR(30),
    age INT
);

CREATE TABLE IF NOT EXISTS RunningPath (   
	map_id INT AUTO_INCREMENT PRIMARY KEY,   
    user_id VARCHAR(20) REFERENCES User(user_id),   
    `path` TEXT,   
    `description` VARCHAR(3000),
    distance INT,   
    reg_date DATETIME 
);
DROP TABLE COMMENT;
CREATE TABLE IF NOT EXISTS Comment (   
	comment_id INT AUTO_INCREMENT,
    map_id INT REFERENCES RunningPath(map_id),
    user_id VARCHAR(20) REFERENCES User(user_id),   
    content VARCHAR(3000), 
    com_reg_date DATETIME,
    CONSTRAINT comment_PK PRIMARY KEY(comment_id, map_id)
);

SELECT * FROM COMMENT;
INSERT INTO COMMENT VALUES(0, 0,1,1,1,"2022-02-22");
INSERT INTO COMMENT VALUES(0,0,2,2,1,"2022-02-22");
INSERT INTO COMMENT VALUES(0,0,1,1,1,"2022-02-22");

CREATE TABLE IF NOT EXISTS Bookmark (
	map_id INT,
	user_id VARCHAR(20),
    marked BOOLEAN,
    FOREIGN KEY (map_id) REFERENCES RunningPath(map_id),
	FOREIGN KEY (user_id) REFERENCES User(user_id),
    CONSTRAINT Bookmark_PK PRIMARY KEY(map_id, user_id)
);