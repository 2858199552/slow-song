package com.fuller.slowsong.mapper;

import com.fuller.slowsong.entity.User;

/**
 * DAO 层暂时弃用
 */
@Deprecated
public interface UserMapper {
    User getInfo(String name, String password);
}
