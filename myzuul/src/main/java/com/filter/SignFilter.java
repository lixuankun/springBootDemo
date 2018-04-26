package com.filter;

import com.config.IngoreSignApiConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.context.RequestContext;
import com.util.PostKey;
import com.util.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lxk on 2018/3/30.
 */
@Component
public class SignFilter extends ZuulFilter {
    protected final static Logger logger = LoggerFactory.getLogger(SignFilter.class);
    @Autowired
    IngoreSignApiConfig signApiConfig;


    public SignFilter() {
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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();
        List<String> ignoreUrlsList = signApiConfig.getList();

        for (String ignoreUrl : ignoreUrlsList) {
            if (requestURI.equalsIgnoreCase(ignoreUrl)) {
                logger.info("当前请求URL {}在IgnoreUrls列表中,跳过权限检查.", requestURI);
                return true;
            }
        }
        String token = request.getParameter("token");
        Map<String, String[]> parameterMap = request.getParameterMap();
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>();
        Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
        parameterMap.remove("token");
        for (Map.Entry<String, String[]> entry : entrySet) {
            sortedMap.put(entry.getKey(),entry.getValue()[0]);
        }
        String sign = SignUtil.createSign("UTF-8", sortedMap, PostKey.POST_5173);
         if (StringUtils.equalsIgnoreCase(token, sign)) {
            //会进行路由，也就是会调用api服务提供者
//             return true;
            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(200);
        } else {
            //不进行路由
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("签名错误");
        }
        return null;
    }


}
