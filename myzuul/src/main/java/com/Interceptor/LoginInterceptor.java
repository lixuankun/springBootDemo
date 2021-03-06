//package com.Interceptor;
//
//import com.config.IngoreInterceptorApiConfig;
//import com.feign.FeignMgmt;
//import com.helper.Constant;
//import com.model.UserEo;
//import com.util.AuthCookieEditor;
//import com.util.HttpGetIpUtil;
//import com.util.IpContext;
//import com.util.UserContext;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * 登录拦截器
// */
//@SuppressWarnings("ALL")
//@Component
//public class LoginInterceptor extends HandlerInterceptorAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
//
//    @Autowired
//    FeignMgmt userMapper;
//    @Autowired
//    IngoreInterceptorApiConfig ingoreInterceptorApiConfig;
//
//    /**
//     * 在请求处理之前进行调用（Controller方法调用之前）
//     *
//     * @param request
//     * @param response
//     * @param handler
//     * @return 只有返回true才会继续向下执行，返回false取消当前请求
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        try {
//            List<String> ignoreUrlsList = ingoreInterceptorApiConfig.getList();
//            String userIp = request.getHeader(Constant.HEADER_IP_KEY);
//            if (StringUtils.isBlank(userIp)) {
//                userIp = HttpGetIpUtil.getIpAddress(request);
//            }
//
//            logger.info("当前请求对应的用户ip【{}】", userIp);
//            IpContext.setCurrentContext(userIp);
//
//            String requestURI = request.getRequestURI();
//            for (String ignoreUrl : ignoreUrlsList) {
//                if (requestURI.equalsIgnoreCase(ignoreUrl)) {
//                    logger.info("当前请求URL {}在IgnoreUrls列表中,跳过权限检查.", requestURI);
//                    return true;
//                }
//            }
//
//            Cookie cookie = AuthCookieEditor.getCookie(request, Constant.TOKEN_NAME);
//            if (cookie != null) {
//                throw new RuntimeException("用户登录异常");
//            }
//            logger.info(String.format("当前请求对应的COOKIE为【%s】", cookie.getName() + "=" + cookie.getValue()));
////            AuthCookieEditor.cookieToSession(cookie, request.getSession());
//            // 设置当前请求上下文信息
//            String uid = request.getHeader("uid");
//            if (null == uid) {
//                uid = (String) request.getAttribute("uid");
//                if (null == uid) {
//                    uid = (String) request.getSession().getAttribute("uid");
//                }
//            }
//            if (uid != null) {
//                UserEo userEo = userMapper.getUser(Long.valueOf(uid));
//                UserContext.setCurrentUser(userEo);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//}
