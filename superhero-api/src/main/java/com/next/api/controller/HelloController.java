package com.next.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HelloController extends BasicController {

    @GetMapping("/hello")
    public Object hello() {
        return "hello world";
    }

    @GetMapping("/redis/set")
    public Object set() {
        redis.set("redis-in-springboot", "hello~~ redis~~");
        return "设置成功";
    }

    @GetMapping("/redis/get")
    public Object get() {
        return redis.get("redis-in-springboot");
    }
}
