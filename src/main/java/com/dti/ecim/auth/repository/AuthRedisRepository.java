package com.dti.ecim.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class AuthRedisRepository {
    private static final String STRING_KEY_PREFIX = "ecim:jwt:string:";
    private static final String BLACKLIST_KEY_PREFIX = "ecim:jwt:blacklist:";
    private final ValueOperations<String, String> valueOperations;

    public AuthRedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void saveJwtKey(String email, String jwtKey) {
        valueOperations.set(STRING_KEY_PREFIX + email, jwtKey, 1, TimeUnit.HOURS);
    }

    public Optional<String> getJwtKey(String email) {
        return Optional.ofNullable(valueOperations.get(STRING_KEY_PREFIX + email));
    }

    public void deleteJwtKey(String email) {
        valueOperations.getAndDelete(STRING_KEY_PREFIX + email);
    }

    public void saveBlacklistKey(String jwtKey) {
        valueOperations.set(BLACKLIST_KEY_PREFIX + jwtKey, jwtKey, 1, TimeUnit.HOURS);
    }

    public boolean checkBlacklistKey(String jwtKey) {
        String token = valueOperations.get(BLACKLIST_KEY_PREFIX + jwtKey);
        return token != null;
    }

    public void deleteBlacklistKey(String jwtKey) {
        valueOperations.getAndDelete(BLACKLIST_KEY_PREFIX + jwtKey);
    }
}
