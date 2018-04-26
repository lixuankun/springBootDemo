package com.feign;

import com.model.BookEo;
import com.model.UserEo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by lxk on 2018/3/29.
 */
@FeignClient(name = "my-boot")
public interface FeignMgmt {
    @GetMapping(value = "root/getAll")
     List<UserEo> getAll();
    @GetMapping(value = "root/getUser")
    UserEo getUser(@RequestParam("userName")String uName, @RequestParam("password") String password);
    @GetMapping(value = "root/getUser")
    UserEo getUser(@RequestParam("id") Long id);
    @PostMapping("book/publishBook")
    void publishGoods(BookEo goodsEo);

    @PostMapping("book/levelingOrOnLine")
    void levelingOrOnLine(BookEo goodsEo);

    @GetMapping("book/selectBook")
    List<BookEo> selectGoods();
    @GetMapping("book/selectBookByUser")
    List<BookEo> selectBookByUser();
}
