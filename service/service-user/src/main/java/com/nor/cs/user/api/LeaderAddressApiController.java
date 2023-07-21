package com.nor.cs.user.api;

import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.user.service.api.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user/leader/inner")
public class LeaderAddressApiController {
    @Resource
    private UserService userService;
    
    @GetMapping("/user-address/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable Long userId) {
        LeaderAddressVo leaderAddress = userService.getLeaderAddressByUserId(userId);
        return leaderAddress;
    }
}
