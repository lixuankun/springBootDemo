/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		BaseDTO
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-5 下午11:58:48
 *	描	述：		抽象服务响应基类
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.context;

/**
 * 抽象服务响应基类
 * @author Shawn
 *
 */
public abstract class AbstractServiceResponse {

	protected ResponseStatus responseStatus;
	
	/**
     * 调用是否成功标识
     */
    private boolean success = true;
    
    /**
     * ext调用异常标识 
     * 业务异常该标识为false，只有在exceptionInterceptor中出现的异常才会是true
     */
    private boolean isException = false;
	
	/**
	 * 返回状态信息
	 * @return the responseStatus
	 */
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	/**
	 * 设置状态信息
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getIsException() {
		return isException;
	}

	public void setIsException(boolean isException) {
		this.isException = isException;
	}

}
