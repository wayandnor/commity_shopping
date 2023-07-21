package com.nor.cs.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.nor.cs.common.constant.RedisConst;
import com.nor.cs.common.exception.CsException;
import com.nor.cs.common.result.Result;
import com.nor.cs.common.result.ResultCodeEnum;
import com.nor.cs.enums.UserType;
import com.nor.cs.enums.user.User;
import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.model.vo.user.UserLoginVo;
import com.nor.cs.uitl.JwtUtil;
import com.nor.cs.user.service.api.UserService;
import com.nor.cs.user.util.ConstantPropertiesUtil;
import com.nor.cs.user.util.HttpClientUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/weixin")
public class WxApiController {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @GetMapping("/wxLogin/{code}")
    public Result userLogin(@PathVariable String code) {
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        StringBuilder url = new StringBuilder()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(), wxOpenAppId, wxOpenAppSecret, code);
        String result = null;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new CsException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openId = jsonObject.getString("openid");
        User user = userService.getUserByOpenId(openId);

        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressByUserId(user.getId());

        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickName(openId);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

        String token = JwtUtil.createToken(user.getId(), user.getNickName());

        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userLoginVo,
                        RedisConst.USERKEY_TIMEOUT,
                        TimeUnit.DAYS);


        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.successWithData(map);
    }
}
