package cn.itcast.hotel.service.impl;

import cn.itcast.hotel.constants.TOKEN_ARGS;
import cn.itcast.hotel.customUtils.RedisUtils;
import cn.itcast.hotel.exception.CustomException;
import cn.itcast.hotel.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static cn.itcast.hotel.constants.TOKEN_ARGS.TOKEN_PREFIX;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    RedisUtils redisUtils;

    //private final String TOKEN_NAME = "ACCESS-Token";

    @Override
    public String createToken(Long id) {
        // 先查id绑定的token是否存在
        String token = redisUtils.get(TOKEN_PREFIX + id);
        if (token == null) {
            token = UUID.randomUUID().toString();
            redisUtils.setEx(TOKEN_PREFIX + id, token, 60L);
        } else {
            throw new CustomException(20001, "不能重复提交");
        }
        return null;
    }

    @Override
    public boolean checkToken(Long id) throws Exception {
        String token = redisUtils.get(TOKEN_PREFIX + id);
        if (StringUtils.hasText(token)) {
            System.out.println("token存在:" + token);
        }

        if (!redisUtils.exists(token)) {
            throw new CustomException(20001, "不能重复提交");
        }

        boolean remove = redisUtils.remove(token);
        if (!remove) {
            throw new CustomException(20001, "Token刷新失败");
        }
        return true;
    }
}
