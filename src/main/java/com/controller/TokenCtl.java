package com.controller;

import com.domain.ResultModel;
import com.domain.ResultStatus;
import com.domain.TokenModel;
import com.domain.User;
import com.mapper.UserMapper;
import com.repository.TokenManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/tokens")
public class TokenCtl {

	@Autowired
	private UserMapper userMapper;

	@Qualifier("redisTokenManager")
	@Autowired
	private TokenManager tokenManager;
	
	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public String a (){
		return "123456";
	}

	@RequestMapping(value="login",method = RequestMethod.POST)
	public ResponseEntity<ResultModel> login(@RequestParam String username, @RequestParam String password) {
		Assert.notNull(username, "username can not be empty");
		Assert.notNull(password, "password can not be empty");
		User user = userMapper.findByName(username);
		if (user == null || // 未注册
				!user.getPassword().equals(password)) { // 密码错误
			// 提示用户名或密码错误
			return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR),
					HttpStatus.NOT_FOUND);
		}
		// 生成一个token，保存用户登录状态
		TokenModel model = tokenManager.createToken(1);
		return new ResponseEntity<ResultModel>(ResultModel.ok(model), HttpStatus.OK);
	}


   /* @RequestMapping(value="logout",method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
*/
	
}
