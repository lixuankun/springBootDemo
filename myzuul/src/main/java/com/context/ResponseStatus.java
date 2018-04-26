/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ErrorResponse
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-5 下午10:23:12
 *	描	述：		返会状态信息
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.context;

/**
 * 返回状态信息类
 * @author Shawn
 *
 */
public class ResponseStatus {
	
	/**
	 * 状态码
	 */
	private String code;
	
	/**
	 * 状态码对应信息
	 */
	private String message;
	
	public ResponseStatus(){
		
	}
	
	public ResponseStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 设置状态信息
	 * @param code		状态码
	 * @param message	状态码对应信息
	 */
	public void setStatus(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 返回状态码
	 * 返回00为正常返回，其他为异常类型
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置状态码
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 返回状态信息
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置状态信息
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
