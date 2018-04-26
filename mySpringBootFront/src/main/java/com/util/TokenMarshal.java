package com.util;


/**
 * Copyright deppon.com. All rights reserved. Description: 令牌序列化与反序列化 HISTORY ID
 * DATE PERSON REASON
 * *******************************************
 * 1 2011-3-11 ztjie 新增
 * *******************************************
 */
final public class TokenMarshal {
    private static final String ENCRYPT_KEY = "Y@j9^8Jv_cQW$45TrYv2UiWvO!";

    private TokenMarshal() {
    }

    /**
     * 序列化(Base64编码)
     * 将token信息加密成字符串编码
     *
     * @param token
     * @return 经Base64编码的加密字符串
     */
    public static String marshal(Token token) {
        //String tokenStr = Base64.encodeBase64Binrary(token.toBytes());
        String tokenStr = null;
        try {
            tokenStr = DESHelper.encrypt(token.toString(), ENCRYPT_KEY);
        } catch (Exception e) {
            throw new RuntimeException("cookie错误");
        }
        return tokenStr;
    }

    /**
     * 反序列化（Base64解码）
     *
     * @param tokenStr
     * @return
     */
    public static Token unMarshal(String tokenStr) {
        try {
            //byte[] bytes = Base64.decodeBase64Binrary(tokenStr);
            String s = DESHelper.decrypt(tokenStr, ENCRYPT_KEY);
            return new Token(s.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException("cookie错误");
        }
    }
}
