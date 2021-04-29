package com.fuller.slowsong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init(){
//        1.创建用户表
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "email VARCHAR(100) NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "createdAt BIGINT NOT NULL, " +
                "UNIQUE (email))");

//        2.创建用户关系表
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS user_relations(" +
                "user_id BIGINT NOT NULL, " +
                "follow_id BIGINT NOT NULL, " +
                "PRIMARY KEY(user_id, follow_id))");

//        3.创建论坛表 forum

//        4.创建帖子表 post
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS forum_posts(" +
                "post_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "initiator_id BIGINT NOT NULL, " +
                "post_title TINYTEXT NOT NULL, " +
                "post_content TEXT NOT NULL, " +
                "post_createdAt BIGINT NOT NULL )");

//        5.创建帖子评论表
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS post_backs(" +
                "back_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "backer_id BIGINT NOT NULL, " +
                "target_post_id BIGINT NOT NULL, " +
                "target_user_id BIGINT, " + // 当为空时说明是对帖子本身回复
                "back_content TEXT NOT NULL, " +
                "back_createdAt BIGINT NOT NULL, " +
                "FOREIGN KEY(target_post_id) REFERENCES forum_posts(post_id))");
    }
}
