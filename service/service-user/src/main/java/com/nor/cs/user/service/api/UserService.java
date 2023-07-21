package com.nor.cs.user.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.enums.user.User;
import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.model.vo.user.UserLoginVo;


public interface UserService extends IService<User> {
    User getUserByOpenId(String openId);

    UserLoginVo getUserLoginVo(Long id);

    LeaderAddressVo getLeaderAddressByUserId(Long id);
}
