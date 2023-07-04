package cn.itcast.hotel.customUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            //ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            //operations.set(key, value);
            boolean b = setEx(key, value, 60L);
            if (b) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setEx(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public String get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        String result = (String) operations.get(key);
        return result;
    }

    public boolean remove(final String key) {
        if (exists(key)) {
            Boolean delete = redisTemplate.delete(key);
            return delete;
        }
        return false;
    }
}
