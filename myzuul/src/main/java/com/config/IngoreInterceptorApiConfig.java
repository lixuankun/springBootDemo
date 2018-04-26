package com.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxk on 2018/4/3.
 */
@Component
public class IngoreInterceptorApiConfig {
    private static final List<String> list=new ArrayList<>();
    static {
        list.add("/myBootFront/front/selectGoods");
    }

    public static List<String> getList() {
        return list;
    }
}
