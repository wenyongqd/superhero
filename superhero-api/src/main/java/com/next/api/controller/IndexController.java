package com.next.api.controller;

import com.next.pojo.Movie;
import com.next.service.CarouselService;
import com.next.service.MovieService;
import com.next.utils.JsonUtils;
import com.next.utils.NEXTJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("index")
@Api(value = "首页", tags = "首页展示的相关接口")
public class IndexController extends BasicController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private MovieService movieService;

//    @GetMapping("/carousel/list")
//    public Object list() {
//        return carouselService.queryAll();
//    }

    //@GetMapping("/carousel/list")
    @PostMapping("/carousel/list")
    @ApiOperation(value = "获取首页轮播图列表", notes ="获取首页轮播图列表", httpMethod = "POST")
    public NEXTJSONResult list() {

        return NEXTJSONResult.ok(carouselService.queryAll());
    }

    @PostMapping("/movie/hot")
    @ApiOperation(value = "热门超英/预告", notes ="热门超英/预告", httpMethod = "POST")
    public NEXTJSONResult hot(
            @ApiParam(name = "type", value = "[超英(superhero)/预告(trailer)]", required = true)
            @RequestParam String type) {

        if (StringUtils.isBlank(type)) {
            return NEXTJSONResult.errorMsg("参数不能为空");
        }
        return  NEXTJSONResult.ok(movieService.queryHotSuperhero(type));
    }

    @PostMapping("/guessULike")
    @ApiOperation(value = "猜你喜欢", notes ="猜你喜欢", httpMethod = "POST")
    public NEXTJSONResult guessULike() {

        // 1. 获得数据库中movie表的所有数量 counts
        Integer counts = movieService.queryAllTrailerCounts();

        // 2. 根据counts, 获取随机的5个 index，每个index和movie表中的逐渐id相互对应
        Integer[] guessIndexArray = getGuessULikeArray(counts);

        // 3. 从Redis中获得具体的movie记录
        List<Movie> guessList = new ArrayList<>();
        for (Integer index : guessIndexArray) {
            String jsonTrailer = redis.get("guess-trailer-id:" + index);
            System.out.println(jsonTrailer);
            Movie trailer = JsonUtils.jsonToPojo(jsonTrailer, Movie.class);
            guessList.add(trailer);
        }

        return  NEXTJSONResult.ok(guessList);
    }


}
