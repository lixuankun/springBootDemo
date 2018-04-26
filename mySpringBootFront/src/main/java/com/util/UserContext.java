package com.util;


import com.model.UserEo;

/**
 * 系统用户信息获得的上下文管理
 * 用户信息的ID存放于应用服务器的Session中
 * 通过Session的ID通过缓存获取用户
 * 缓存中没有指定用户信息时，会自动通过Provider去获取信息
 * 用户在缓存中存在的时候受DataReloader决定
 *
 */
public final class UserContext {
    
	 private static ThreadLocal<UserContext> context = new ThreadLocal<UserContext>(){
        @Override
        protected UserContext initialValue(){
            return new UserContext();
        }
    };
	
	private UserEo user;
	
	private String authKey;
	
    public UserEo getUser() {
		return user;
	}

	public String getAuthKey() {
		return authKey;
	}

	private UserContext() {}
	
    public static UserContext getCurrentContext() {
		return context.get();
	}

	/**
     * 获取当前会话的用户
     * 如果没有用户信息返回值为NULL
     * @return
     */
	public static UserEo getCurrentUser() {
        return getCurrentContext().getUser();
    }
	
	/**
     * 获取当前会话用户的ID
     * 如果没有返回值为NULL
     * @return
     */
	public static Long getCurrentUserId() {
		UserEo user =  getCurrentContext().getUser();
		if(user!=null){
			return user.getId();

		}
        return null;
    }
	
	/**
     * 获取当前会话用户的ID
     * 如果没有返回值为NULL
     * @return
     */
	public static String getCurrentUserLoginAccount() {
		UserEo user = getCurrentContext().getUser();
		if(user!=null){
			return user.getUserName();
		}
        return null;
    }
	
	/**
     * 获取当前会话Key
     * 如果没有返回值为NULL
     * @return
     */
	public static String getCurrentAuthKey() {
		return getCurrentContext().getAuthKey();
    }
    
	/**
	 * 
	 * <p>设置请求上下文</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午9:53:17
	 * @param user 请求用户信息
	 * @see
	 */
	public static void setCurrentUser(UserEo user) {
	    setCurrentContext(user,null);
	}
	
	/**
	 * 
	 * <p>设置请求上下文</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午9:51:41
	 * @param user 请求用户信息
	 * @param authKey 请求authKey
	 * @see
	 */
	public static void setCurrentContext(UserEo user, String authKey) {
	    UserContext userContext = getCurrentContext();
	    userContext.user = user;
	    userContext.authKey = authKey;
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
}
