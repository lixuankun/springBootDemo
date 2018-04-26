package com.service;

import com.feign.FeignMgmt;
import com.model.BookEo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lxk on 2018/3/29.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    FeignMgmt feignMgmt;



    @GetMapping("publishBook")
    void publishGoods(BookEo goodsEo){
        feignMgmt.publishGoods(goodsEo);
    }

    @GetMapping("levelingOrOnLine")
    void levelingOrOnLine(BookEo goodsEo){
        feignMgmt.levelingOrOnLine(goodsEo);
    }
    @GetMapping("selectBook")
    List<BookEo> selectGoods(){
        return feignMgmt.selectGoods();
    }
    @GetMapping("selectBookByUser")
    public List<BookEo> selectGoodsByUser(){
        return feignMgmt.selectBookByUser();
    }

}
