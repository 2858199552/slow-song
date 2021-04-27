package com.fuller.slowsong.service;

import com.fuller.slowsong.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.util.List;

@Component
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    public User getUserById(long id){
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[]{id}, userRowMapper);
    }

    public User getUserByEmail(String email){
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new Object[]{email}, userRowMapper);
    }

    public User signIn(String email, String password){
        logger.info("try login by {}...", email);
        User user = getUserByEmail(email);
        if (user.getPassword().equals(password)){
            return user;
        }
        throw new RuntimeException("login failed");
    }

    public User register(String email, String password, String name){
        logger.info("try register by {}...", email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setCreatedAt(System.currentTimeMillis());
        KeyHolder holder = new GeneratedKeyHolder();
        if (1 != jdbcTemplate.update((conn) -> {
            var ps = conn.prepareStatement("INSERT INTO users(email, password, name, createdAt) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, user.getEmail());
            ps.setObject(2, user.getPassword());
            ps.setObject(3, user.getName());
            ps.setObject(4, user.getCreatedAt());
            return ps;
        }, holder)){
            throw new RuntimeException("Insert failed.");
        }
        user.setId(holder.getKey().longValue());
        return user;
    }

    public void cancel(String email, String password){
        logger.info("try cancel by {}...", email);
        User user = getUserByEmail(email);
        if (!user.getPassword().equals(password)){
            //throw new RuntimeException("Cancel failed.[The email or password my be incorrect]");
            return;
        }
        if (1 != jdbcTemplate.update("DELETE FROM users WHERE email = ?", email)) {
            throw new RuntimeException("User not found by email");
        }
    }

    public void updateUser(User user){
        if (1 != jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?", user.getName(), user.getId())){
            throw new RuntimeException("User not found by id");
        }
    }

    public List<User> getUsers(){
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    //region 功能性方法
    public List<User> getFollows(long id){
//        return jdbcTemplate.query("SELECT relations_r.follow_id FROM users user_l INNER JOIN user_relations relations_r ON user_l.id = relations_r.user_id", userRowMapper);
        return jdbcTemplate.query("SELECT * FROM users u WHERE u.id = r.follow_id FROM user_relations r WHERE r.user_id = ?", new Object[]{id}, userRowMapper);
    }
    //endregion
}
