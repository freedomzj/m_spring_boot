package com.domain;

public enum ResultStatus {

	SUCCESS(0, "成功"), USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"), USER_NOT_FOUND(-1002,
			"用户不存在"), USER_NOT_LOGIN(-1003, "用户未登录"),OBJECT_NOT_FOUND(-1004,"对象不存在"),OBJECT_PARAM_ERROR(-1004,"对象参数错误");

	/**
	 * 返回码
	 */
	private int code;

	/**
	 * 返回结果描述
	 */
	private String message;

	ResultStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
