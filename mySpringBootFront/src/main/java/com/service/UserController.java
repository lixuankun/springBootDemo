package com.service;

import com.feign.FeignMgmt;
import com.model.UserEo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lxk on 2018/3/29.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("front")
public class UserController {
    @Autowired
    FeignMgmt feignMgmt;

    @RequestMapping("getAll")
    public List<UserEo> getAll() {
        return feignMgmt.getAll();
    }

    @RequestMapping("getUser")
    public UserEo getUser(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("token") String token) {
        return feignMgmt.getUser(userName, password);
    }

}
