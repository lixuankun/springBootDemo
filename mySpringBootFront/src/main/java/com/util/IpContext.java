package com.util;

public class IpContext {
    private static ThreadLocal<IpContext> context = new ThreadLocal<IpContext>(){
        @Override
        protected IpContext initialValue(){
            return new IpContext();
        }
    };

    public static IpContext getCurrentContext() {
        return context.get();
    }

    private String ip;
    private IpContext() {

    }
    /**
     *
     * <p>设置请求上下文</p>
     * @author ztjie
     * @date 2015-10-19 上午9:51:41
     * @param ip 请求ip
     * @see
     */
    public static void setCurrentContext(String ip) {
        IpContext ipContext = getCurrentContext();
        ipContext.ip = ip;
    }

    /**
     * 获取当前用户IP
     * @return
     */
    public static String getUserIp() {
        return getCurrentContext().getIp();
    }

    /**
     * 清除ThreadLocal中的数据
     * clean
     * @return void
     * @since:0.6
     */
    public static void clean(){
        context.remove();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
