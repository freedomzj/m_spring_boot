package com.repository;

import com.common.CommUtil;
import com.common.Constants;
import com.domain.TokenModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 */
@SuppressWarnings("all")
@Component
public class RedisTokenManager implements TokenManager {

	
	public RedisTokenManager(){
		System.out.println("init+++++++++++ RedisTokenManager+++++++++++");
	}
	
    @Qualifier("redisTemplate")
	@Autowired
    private RedisTemplate<String, String> redis;

//    @Autowired
    /*public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }*/

    public TokenModel createToken(Integer userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(CommUtil.null2String(userId)).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        Integer userId = Integer.parseInt(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps(CommUtil.null2String(model.getUserId())).get().toString();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(CommUtil.null2String(model.getUserId())).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(Integer userId) {
        redis.delete(CommUtil.null2String(userId));
    }

}
