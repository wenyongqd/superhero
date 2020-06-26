package com.next.api;

import com.next.pojo.Movie;
import com.next.redis.RedisOperator;
import com.next.service.MovieService;
import com.next.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoBootRunner implements ApplicationRunner {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RedisOperator redis;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1. 获取所有的电影记录
        List<Movie> allList = movieService.queryAllTrailers();
        //2. 向redis存入每一条电影记录
        for (int i = 0; i <allList.size(); i++) {
            Movie movie = allList.get(i);
            redis.set("guess-trailer-id:" +i, JsonUtils.objectToJson(movie));
        }

    }
}
