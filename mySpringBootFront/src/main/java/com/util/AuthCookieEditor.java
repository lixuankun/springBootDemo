/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * PROJECT NAME	: bse-baseinfo-api
 * <p>
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/domain/Cookie.java
 * <p>
 * FILE NAME        	: Cookie.java
 * <p>
 * AUTHOR			: FOSS综合管理开发组
 * <p>
 * HOME PAGE		:  http://www.deppon.com
 * <p>
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.util;

import com.helper.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Cookie操作类 主要功能： 1.生成Cookie {@link } 2.Cookie数据到Session
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:053990,date:2013-12-3 下午3:45:54
 * </p>
 */
public final class AuthCookieEditor {

    private static final Logger LOG = LoggerFactory
            .getLogger(AuthCookieEditor.class);

    private AuthCookieEditor() {
    }

    private static AuthCookieEditor cookie;

    public static AuthCookieEditor getInstance() {
        if (cookie == null) {
            cookie = new AuthCookieEditor();
        }
        return cookie;
    }

    /**
     * 获取Token字符串
     *
     * @return
     * @author ztjie
     * @date 2012-12-20 下午3:37:53
     * @see
     */
    public static String getTokenParam(HttpSession session) {
        String uid = (String) session.getAttribute(Constant.SERVICE_REQUEST_HEADER_UID);
        String authkey = (String) session.getAttribute(Constant.SERVICE_REQUEST_HEADER_AUTHKEY);
        Token token = new Token(uid, authkey, session.getMaxInactiveInterval());
        return TokenMarshal.marshal(token);
    }

    /**
     * 保存cookie 主要功能： 1.根据session重新生成cookie 2.修改cookie的时间戳
     *
     * @param request
     * @param response
     * @author ztjie
     * @date 2012-11-28 下午8:57:44
     * @see
     */
    public static String saveCookie(HttpServletRequest request, HttpServletResponse response) {

        String tokenParam = getTokenParam(request.getSession());
        try {
            tokenParam = URLEncoder.encode(tokenParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("cookie encode error", e);
            throw new RuntimeException("cookie异常");
        }
        Cookie cookie = getCookie(request);
        if (cookie != null) {
            // 修改cookie时间戳
            cookie.setValue(tokenParam);
        } else {
            // 重新new一个Cookie
            cookie = new Cookie(Constant.TOKEN_NAME, tokenParam);
        }
        cookie.setPath("/");// 同一个域名所有url cookie共享
        cookie.setMaxAge(365 * 24 * 60 * 60);// 365天后失效
        response.addCookie(cookie);
        return tokenParam;
    }

    /**
     * 失效Cookie
     *
     * @author ztjie
     * @date 2012-11-30 上午10:12:24
     * @see
     */
    public static void invalidateCookie(HttpServletRequest request, HttpServletResponse response) {
        // 失效掉token的cookie
        Cookie cookie_token = getCookie(request);
        if (cookie_token != null) {
            cookie_token.setMaxAge(0);// 设置为0立即删除
            response.addCookie(cookie_token);
        }
        Cookie cookie_jsession = getCookie(request, Constant.JSESSIONID);
        if (cookie_jsession != null) {
            cookie_jsession.setMaxAge(0);// 设置为0立即删除
            response.addCookie(cookie_jsession);
        }
    }

    /**
     * 获取HttpCookie对象,token对应的cookie
     *
     * @return
     * @author ztjie
     * @date 2012-12-6 上午9:49:55
     * @see
     */
    public static Cookie getCookie(HttpServletRequest request) {
        return getCookie(request, Constant.TOKEN_NAME);
    }

    /**
     * 获取HttpCookie对象,token对应的cookie值
     *
     * @return
     * @author ztjie
     * @date 2012-12-6 上午9:49:55
     * @see
     */
    public static String getCookieValue(HttpServletRequest request) {
        Cookie cookie = getCookie(request, Constant.TOKEN_NAME);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 获取HttpCookie对象,根据传入的cookie的name值获取, 参数可以通过ServicesContants获取
     *
     * @param name
     * @return
     * @author ztjie
     * @date 2012-12-13 上午11:54:31
     * @see
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    /**
     * Cookie数据到Session 主要功能： 1.Cookie不存在，Throw UserNotLoginException异常
     * 2.Cookie存在，赋值到Session
     *
     * @author ztjie
     * @date 2012-11-30 上午8:31:15
     * @see
     */
    public static void cookieToSession(Cookie cookie, HttpSession session) {
        if (cookie != null) {
            String paramToken = cookie.getValue();
            LOG.debug("cookieToSession paramToken = " + paramToken);
            if (StringUtils.isBlank(paramToken)) {
                throw new RuntimeException("用户未登陆");// 用户未登录
            } else {
                try {
                    paramToken = URLDecoder.decode(paramToken, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOG.error("cookie encode error", e);
                    throw new RuntimeException("cookie异常");
                }
                Token token = TokenMarshal.unMarshal(paramToken);
                LOG.debug("cookieToSession token = " + token);
                if (token != null && !token.expired()) {
                    AuthCookieEditor.getInstance().tokenToSession(session, token);
                } else {
                    throw new RuntimeException("用户未登陆");// 用户未登录
                }
            }
        } else {
            throw new RuntimeException("用户未登录");
        }
    }

    /**
     * Cookie数据到Session 主要功能： 1.Cookie不存在，Throw UserNotLoginException异常
     * 2.Cookie存在，赋值到Session
     *
     * @author ztjie
     * @date 2012-11-30 上午8:31:15
     * @see
     */
    public static void cookieToSessionNoValidate(Cookie cookie, HttpSession session) {
        if (cookie != null) {
            String paramToken = cookie.getValue();
            if (StringUtils.isBlank(paramToken)) {
                return;
            } else {
                try {
                    paramToken = URLDecoder.decode(paramToken, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOG.error("cookie encode error", e);
                    throw new RuntimeException("cookie异常");
                }
                Token token = TokenMarshal.unMarshal(paramToken);
                if (token != null) {
                    AuthCookieEditor.getInstance().tokenToSession(session, token);
                }
            }
        }
    }

    /**
     * token的内容复制到session中
     *
     * @param token
     * @author ztjie
     * @date 2012-11-29 上午9:12:20
     * @see
     */
    private void tokenToSession(HttpSession session, Token token) {
        session.setAttribute(Constant.SERVICE_REQUEST_HEADER_UID,
                token.getUid());
        session.setAttribute(Constant.SERVICE_REQUEST_HEADER_AUTHKEY,
                token.getAuthkey());
    }


}
