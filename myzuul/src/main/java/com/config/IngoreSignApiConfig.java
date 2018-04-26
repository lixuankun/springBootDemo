package com.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxk on 2018/4/3.
 */
@Component
public class IngoreSignApiConfig {
    private static final List<String> list=new ArrayList<>();
    static {
        list.add("/myBootFront/front/getAll");
        list.add("/myBootFront/book/selectBook");
        list.add("/myBootFront/book/selectBookByUser");
        list.add("/myBootFront/book/publishBook");
        list.add("/myBootFront/book/levelingOrOnLine");
    }

    public static List<String> getList() {
        return list;
    }
}
