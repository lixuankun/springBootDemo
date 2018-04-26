package com.filter;

import com.config.IngoreSignApiConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lxk on 2018/3/30.
 */
@Component
public class ErrorFilter extends ZuulFilter {
    protected final static Logger logger = LoggerFactory.getLogger(ErrorFilter.class);
    @Autowired
    IngoreSignApiConfig signApiConfig;


    public ErrorFilter() {
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
        return "error";
    }

    //通过int值来定义过滤器的执行顺序   同filterType类型中，order值越大，优先级越低
    @Override
    public int filterOrder() {
        return 0;
    }

    //返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() != null && !ctx.getBoolean("SEND_ERROR_FILTER_RAN", false);
    }

    //过滤器的具体逻辑
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        try {
            ZuulException exception = findZuulException(ctx.getThrowable());

            if (exception == null) {
                return true;
            }
            request.setAttribute("javax.servlet.error.status_code", exception.nStatusCode);

            logger.error("Error during filtering", exception);
            request.setAttribute("javax.servlet.error.exception", exception);

            if (StringUtils.hasText(exception.errorCause)) {
                request.setAttribute("javax.servlet.error.message", exception.getCause().getMessage());
            }

        } catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return response;
    }

    private ZuulException findZuulException(Throwable e) {
        if (e instanceof ZuulException) {
            return (ZuulException) e;
        }
        return null;
    }
}
