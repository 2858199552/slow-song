package com.fuller.slowsong.entity;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class ForumPost {
    private Long postId;
    private Long initiator_id;

    private String postTitle;
    private String postContent;

    private Long createdAt;

    public String getCreatedDateTime(){
        return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
