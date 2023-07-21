package com.nor.cs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.enums.user.Leader;
import com.nor.cs.enums.user.User;
import com.nor.cs.enums.user.UserDelivery;
import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.model.vo.user.UserLoginVo;
import com.nor.cs.user.mapper.LeaderMapper;
import com.nor.cs.user.mapper.UserDeliveryMapper;
import com.nor.cs.user.mapper.UserMapper;
import com.nor.cs.user.service.api.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private LeaderMapper leaderMapper;
    
    @Resource
    private UserDeliveryMapper userDeliveryMapper;

    @Override
    public User getUserByOpenId(String openId) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openId));
        return user;
    }

    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if(userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }

    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long id) {
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if(userDelivery == null) {
            return null;
        }
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(id);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }
}
