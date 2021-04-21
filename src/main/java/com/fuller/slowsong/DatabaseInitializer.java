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
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "email VARCHAR(100) NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "createdAt BIGINT NOT NULL, " +
                "UNIQUE (email))");
    }
}
