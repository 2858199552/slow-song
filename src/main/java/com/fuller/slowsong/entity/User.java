package com.fuller.slowsong.entity;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class User {
    private Long id;

    private String email;
    private String password;
    private String name;

    private Long createdAt;

    public String getCreatedDateTime(){
        return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
