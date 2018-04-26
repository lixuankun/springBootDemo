/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/domain/Token.java
 * 
 * FILE NAME        	: Token.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.util;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;


/**
 * CookieToken
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2012-11-28 下午9:01:15</p>
 * @author ztjie
 * @date 2012-11-28 下午9:01:15
 * @since
 * @version
 */
public class Token {
	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Token.class);
	
	/**
	 * 用户ID
	 */
	private String uid;
	
	/**
	 * 登录授权key
	 */
	private String authkey;

	/**
	 * 有效时间，单位毫秒，默认为当前系统毫秒数
	 */
	private Long expireTime = System.currentTimeMillis();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long millisecond) {
		this.expireTime = millisecond;
	}

	/**
	 * 根据appId,userId,empCode,currentDeptCode,second秒来创建token
	 * 
	 * @author ztjie
	 * @date 2012-11-29 下午8:49:40
	 * @param uid	工号
	 * @param authkey	登录授权key
	 * @param expireSecond	有效时间 单位秒
	 */
	public Token(String uid, String authkey, int expireSecond) {
		this.uid = uid;
		this.authkey = authkey;
		//生成时间戳
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int millisecond = expireSecond * 1000;
		c.add(Calendar.MILLISECOND, millisecond);
		this.expireTime = c.getTimeInMillis();
	}
	
	/**
	 * byte[]数组的内容复制到Token中
	 * 
	 * @author ztjie
	 * @date 2012-11-30 上午8:34:29
	 * @param tokenBytes
	 */
	public Token(byte[] tokenBytes) {
		try {
			String token = new String(tokenBytes, CharEncoding.UTF_8);
			String[] keys = token.split(",");
			this.uid = keys[0];
			this.authkey = keys[1];
			this.expireTime = Long.parseLong(keys[2]);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	/**
	 * 返回该对象的byte[]数组表示
	 * 
	 * @author ztjie
	 * @date 2012-11-29 下午8:49:22
	 * @return
	 * @see
	 */
	public byte[] toBytes() {
		try {
			return this.toString().getBytes(CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}

	/** 
	 * 返回该对象的字符串表示
	 * 
	 * @author ztjie
	 * @date 2012-11-29 下午8:49:11
	 * @return 
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		super.toString();
		StringBuffer sb = new StringBuffer(8);
		sb.append(getUid()).append(",");
		sb.append(getAuthkey()).append(",");
		sb.append(getExpireTime());
		return sb.toString();
	}
	
	/**
	 * token是否过期
	 * 		1.expireTime >= currentTime 未过期返回false
	 * 		2.expireTime < currentTime 已过期返回true
	 * 
	 * @author ztjie
	 * @date 2012-11-30 上午11:41:31
	 * @return 1.expireTime >= currentTime 未过期返回false 2.expireTime < currentTime 已过期返回true
	 */
	public boolean expired() {
		long millisecond = this.getExpireTime();
		long currentMs = System.currentTimeMillis();
		return millisecond >= currentMs ? false : true;
	}
	
}
