package com.nor.cs.client.user;

import com.nor.cs.model.vo.user.LeaderAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-user")
@RequestMapping("/api/user/leader/inner")
public interface UserFeignClient {
    @GetMapping("/user-address/{userId}")
    LeaderAddressVo getUserAddressByUserId(@PathVariable Long userId);
}
