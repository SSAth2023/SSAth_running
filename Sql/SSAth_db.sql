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
    user_id VARCHAR(20) REFERENCES User(user_id) ON DELETE CASCADE,   
    `path` TEXT,   
    `description` VARCHAR(3000),
    distance INT,   
    reg_date DATETIME 
);

CREATE TABLE IF NOT EXISTS Comment (   
	comment_id INT AUTO_INCREMENT,
    map_id INT REFERENCES RunningPath(map_id) ON DELETE CASCADE,
    user_id VARCHAR(20) REFERENCES User(user_id) ON DELETE CASCADE,   
    content VARCHAR(3000), 
    com_reg_date DATETIME,
    CONSTRAINT comment_PK PRIMARY KEY(comment_id, map_id)
);

CREATE TABLE IF NOT EXISTS Bookmark (
	map_id INT,
	user_id VARCHAR(20),
    marked BOOLEAN,
    FOREIGN KEY (map_id) REFERENCES RunningPath(map_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    CONSTRAINT Bookmark_PK PRIMARY KEY(map_id, user_id)
);