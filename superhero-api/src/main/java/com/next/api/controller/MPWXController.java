package com.next.api.controller;

import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.pojo.bo.WXSessionBO;
import com.next.pojo.vo.UsersVO;
import com.next.service.MovieService;
import com.next.service.StaffService;
import com.next.service.UserService;
import com.next.utils.HttpClientUtil;
import com.next.utils.JsonUtils;
import com.next.utils.MPWXConfig;
import com.next.utils.NEXTJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
//@RequestMapping("stu")
@Api(value = "微信小程序授权", tags = {"微信小程序授权"})
public class MPWXController extends BasicController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @PostMapping("/mpWXLogin/{code}")
    @ApiOperation(value = "微信登录", notes ="微信登录", httpMethod = "POST")
    public NEXTJSONResult list(
            @ApiParam(name = "code", value = "jscode, 授权凭证", required = true)
            @PathVariable String code,
            @RequestBody MPWXUserBO wxUserBO)
     {
        String appId = MPWXConfig.APPID;
        String secret = MPWXConfig.SECRET;

//         https://api.weixin.qq.com/sns/jscode2session？
//         appid=APPID&
//         secret=SECRET&
//         js_code=JSCODE&
//         grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session";

         Map<String, String> param = new HashMap<>();
         param.put("appid", appId);
         param.put("secret", secret);
         param.put("js_code", code);
         param.put("grant_type", "authorization_code");
         String wxResult = HttpClientUtil.doGet(url, param);

         WXSessionBO model = JsonUtils.jsonToPojo(wxResult, WXSessionBO.class);
         String openId = model.getOpenid();

         //根据openId查询用户是否存在，存在则登陆，不存在则注册
         Users user = userService.queryUserForLoginByMPWX(openId);
         if( user == null ) {
             user = userService.saveUserMPWX(openId, wxUserBO);
         }

         //保存用户的分布式会话 - 生成一个token保存到redis中，可以被任何分布式集群节点访问到
         String userId = user.getId();
         //生成一个token
         String uniqueToken = UUID.randomUUID().toString().trim();
         // 保存用户会话
         redis.set(REDIS_USER_TOKEN + ":" + userId, uniqueToken);

         UsersVO usersVO = new UsersVO();
         BeanUtils.copyProperties(user, usersVO);
         usersVO.setUserUniqueToken(uniqueToken);

        return  NEXTJSONResult.ok(usersVO);
    }
}
