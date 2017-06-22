package com.mapper;

import com.common.Encryption;
import com.domain.PersistentLogins;
import com.domain.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zengjie on 17/6/22.
 */
public class TokenManage implements TokenMapper {

    @Autowired
    private UserMapper userMapper;

    public TokenModel createToken(Integer userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        try {
            //增加加密措施
            token=Encryption.encrypt(token);
        } catch (Exception e) {
        }
        TokenModel model = new TokenModel(userId, token);
        PersistentLogins persistentLogins=new PersistentLogins();
        persistentLogins.setLastUsed(new Date().toString());
        persistentLogins.setToken(model.getToken());
        persistentLogins.setUserId(userId);
        userMapper.saveToken(persistentLogins);
        //存储到MySQL并设置过期时间
//        userDao.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
      /*  String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token
        Integer userId = Integer.valueOf(param[0]);

        */
        String token ="";
        try {
            //解密
            token= Encryption.desEncrypt(authentication);
        } catch (Exception e) {
            return new TokenModel(1, token);
        }
        return new TokenModel(1, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = "";
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
//        redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(Integer userId) {
//        redis.delete(userId);
    }
}
