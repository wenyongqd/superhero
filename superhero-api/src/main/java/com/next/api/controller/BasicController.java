package com.next.api.controller;


import com.next.redis.RedisOperator;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    public Integer[] getGuessULikeArray(Integer counts) {
        Integer[] guessIndexArray = new Integer[5];

        for (int i = 0; i < guessIndexArray.length; i++) {
            int numIndex = (int)(Math.random() * counts);

            if(ArrayUtils.contains(guessIndexArray, numIndex)) {
                i--;
                continue;
            }
            guessIndexArray[i] = numIndex;
        }

        return guessIndexArray;
    }
}
