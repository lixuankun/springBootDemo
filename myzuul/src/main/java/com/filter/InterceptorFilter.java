package com.filter;

import com.config.IngoreInterceptorApiConfig;
import com.feign.FeignMgmt;
import com.helper.Constant;
import com.model.UserEo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.util.AuthCookieEditor;
import com.util.HttpGetIpUtil;
import com.util.IpContext;
import com.util.UserContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lxk on 2018/3/30.
 */
@SuppressWarnings("ALL")
@Component
public class InterceptorFilter extends ZuulFilter {
    protected final static Logger logger = LoggerFactory.getLogger(InterceptorFilter.class);
    @Autowired
    IngoreInterceptorApiConfig ingoreInterceptorApiConfig;
    @Autowired
    FeignMgmt userMapper;

    public InterceptorFilter() {
        super();
    }

    @Override
    public boolean isStaticFilter() {
        return super.isStaticFilter();
    }

    @Override
    public String disablePropertyName() {
        return super.disablePropertyName();
    }

    @Override
    public boolean isFilterDisabled() {
        return super.isFilterDisabled();
    }

    @Override
    public ZuulFilterResult runFilter() {
        return super.runFilter();
    }

    @Override
    public int compareTo(ZuulFilter filter) {
        return super.compareTo(filter);
    }

    //pre：可以在请求被路由之前调用
    //route：在路由请求时候被调用
    //post：在route和error过滤器之后被调用
    //error：处理请求时发生错误时被调用
    @Override
    public String filterType() {
        return "pre";
    }

    //通过int值来定义过滤器的执行顺序   同filterType类型中，order值越大，优先级越低
    @Override
    public int filterOrder() {
        return 0;
    }

    //返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器的具体逻辑
    @Override
    public Object run() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        List<String> ignoreUrlsList = ingoreInterceptorApiConfig.getList();
        String userIp = request.getHeader(Constant.HEADER_IP_KEY);
        if (StringUtils.isBlank(userIp)) {
            userIp = HttpGetIpUtil.getIpAddress(request);
        }

        logger.info("当前请求对应的用户ip【{}】", userIp);
        IpContext.setCurrentContext(userIp);

        String requestURI = request.getRequestURI();
        for (String ignoreUrl : ignoreUrlsList) {
            if (requestURI.equalsIgnoreCase(ignoreUrl)) {
                logger.info("当前请求URL {}在IgnoreUrls列表中,跳过权限检查.", requestURI);
                return true;
            }
        }

        Cookie cookie = AuthCookieEditor.getCookie(request, Constant.TOKEN_NAME);
        if (cookie == null) {
            throw new RuntimeException("用户登录异常");
        }
        logger.info(String.format("当前请求对应的COOKIE为【%s】", cookie.getName() + "=" + cookie.getValue()));
        AuthCookieEditor.cookieToSession(cookie, request.getSession());
        // 设置当前请求上下文信息
        String uid = request.getHeader("uid");
        if (null == uid) {
            uid = (String) request.getAttribute("uid");
            if (null == uid) {
                uid = (String) request.getSession().getAttribute("uid");
            }
        }
        if (uid != null) {
            UserEo userEo = userMapper.getUser(Long.valueOf(uid));
            if (userEo==null){
                throw new RuntimeException("用户登录异常");
            }
            UserContext.setCurrentUser(userEo);
        }
        return true;
    }
}
