package com.dti.ecim.discount.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class PointRedisRepository {
    private static final String POINTS_KEY = "ecim:points:";
    private final ValueOperations<String, Integer> valueOperations;

    public PointRedisRepository(RedisTemplate<String, Integer> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void save(String email, Integer points) {
        valueOperations.set(POINTS_KEY + email, points, 1, TimeUnit.HOURS);
    }

    public Optional<Integer> getPoints(String email) {
        return Optional.ofNullable(valueOperations.get(POINTS_KEY + email));
    }

    public void delete(String email) {
        valueOperations.getAndDelete(POINTS_KEY + email);
    }
}
