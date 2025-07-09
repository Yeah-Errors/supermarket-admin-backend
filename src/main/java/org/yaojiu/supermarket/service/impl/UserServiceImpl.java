package org.yaojiu.supermarket.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yaojiu.supermarket.entity.UserDTO;
import org.yaojiu.supermarket.entity.UserEntity;
import org.yaojiu.supermarket.exception.LoginException;
import org.yaojiu.supermarket.mapper.UserMapper;
import org.yaojiu.supermarket.service.UserService;
import org.yaojiu.supermarket.utils.SecurityUtil;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Override
    public UserDTO login(UserEntity userEntity) {
        UserEntity user = getOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getUsername, userEntity.getUsername()));
        if (user != null && SecurityUtil.verify(user.getPassword(), userEntity.getUsername())) return user.toDTO();
        throw new LoginException("账号密码有误，请重试") ;
    }

     @Override
    public boolean register(UserEntity userEntity) {
         UserEntity one = getOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getUsername, userEntity.getUsername()));
         if(one != null) throw new LoginException("用户已存在");
         return save(userEntity);
     }
}
