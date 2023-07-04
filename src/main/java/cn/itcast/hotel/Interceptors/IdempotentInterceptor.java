package cn.itcast.hotel.Interceptors;

import cn.itcast.hotel.customUtils.RedisUtils;
import cn.itcast.hotel.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static cn.itcast.hotel.constants.TOKEN_ARGS.TOKEN_PREFIX;


@Aspect
@Component
public class IdempotentInterceptor {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TokenService tokenService;

    @Pointcut("@annotation(cn.itcast.hotel.customUtils.RepeatSubmit)")
    public void repeatSubmitPointcut() {
    }

    @Around("repeatSubmitPointcut()")
    public Object handleRepeatSubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 假设 ID 参数在第一个位置
        Long id = (Long) args[0];
        // 根据实际情况进行处理...
        //String token = redisUtils.get(TOKEN_PREFIX + id);
        tokenService.createToken(id);
        return joinPoint.proceed(args);
    }
}
