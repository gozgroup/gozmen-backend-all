package com.gozmen.ucenter.service;

import com.gozmen.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenManager {

    private StringRedisTemplate redis;

    @Autowired
    public void setRedis(StringRedisTemplate redis) {
        this.redis = redis;
    }

    String createTokenFor(String username) {

        deleteTokenFor(username);

        //使用uuid作为源token
        String token = UUID.randomUUID().toString();
        redis.boundValueOps(getTokenKey(token)).set(username);
        redis.boundValueOps(getUserKey(username)).set(token);
        return token;
    }

    void deleteTokenFor(String username) {
        if (redis.hasKey(getUserKey(username))) {
            String token = redis.boundValueOps(getUserKey(username)).get();
            if (!StringUtils.isEmpty(token)) {
                redis.delete(getTokenKey(token));
            }
            redis.delete(getUserKey(username));
        }
    }

    String getUsernameByToken(String token) {
        return redis.boundValueOps(getTokenKey(token)).get();
    }


    private String getTokenByUsername(String username) {
        return redis.boundValueOps(getUserKey(username)).get();
    }

    private String getTokenKey(String token) {
        return "token@" + token;
    }

    private String getUserKey(String username) {
        return "user@" + username;
    }

}
