package com.feign;

import com.model.UserEo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lxk on 2018/3/29.
 */
@FeignClient(name = "my-boot")
public interface FeignMgmt {
    @GetMapping(value = "root/getUser")
    UserEo getUser(@RequestParam("id") Long id);

}
