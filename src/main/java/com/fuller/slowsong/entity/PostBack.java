package com.fuller.slowsong.entity;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class PostBack {
    private Long backId;
    private Long backerId;
    private Long targetPostId;
    private Long targetUserId;

    private String backContent;

    private Long createdAt;

    public String getCreatedDateTime(){
        return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}