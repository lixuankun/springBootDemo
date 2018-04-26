package com.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by lxk on 2018/3/30.
 */
public class SignUtil {
    public static String createSign(String characterEncoding, SortedMap<Object,Object> parameters, String partnerKey){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key="+partnerKey);
        String sign = Md5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
}
