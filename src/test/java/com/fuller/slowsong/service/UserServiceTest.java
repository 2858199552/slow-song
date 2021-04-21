package com.fuller.slowsong.service;

import com.fuller.slowsong.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DisplayName("Test UserService")
class UserServiceTest {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    private static User user;

    @BeforeAll
    static void initDatabase() {
        user = new User();
        user.setName("TEST_USER");
        user.setEmail("test@gamil.com");
        user.setPassword("123456");
    }

    @Test
    @DisplayName("2.测试登录")
    void signIn() {
        User user1 = userService.register("tom@QQ.com", "password", "tom");
        assertDoesNotThrow(() -> userService.signIn(user1.getEmail(), user1.getPassword()));
    }

    @Test
    @DisplayName("1.测试账户注册")
    void register() {
        assertDoesNotThrow(() -> userService.register(user.getEmail(), user.getPassword(), user.getName()));
    }

    @Test
    @DisplayName("4.测试账户注销")
    void cancel() {
        User user1 = userService.register("delete@QQ.com", "password", "delete");
        assertDoesNotThrow(() -> userService.cancel(user1.getEmail(), user1.getPassword()));
    }

    @Test
    @DisplayName("3.更新用户信息")
    void updateUser() {
        user = userService.register(user.getEmail(), user.getPassword(), user.getName());
        assertDoesNotThrow(() -> userService.updateUser(user));
    }
}