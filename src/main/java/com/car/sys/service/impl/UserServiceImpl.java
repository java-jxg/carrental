package com.car.sys.service.impl.impl;

import com.car.sys.domain.User;
import com.car.sys.domain.UserExample;
import com.car.sys.mapper.UserMapper;
import com.car.sys.service.UserService;
import com.car.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(UserVo userVo) {
        String pwd = DigestUtils.md5DigestAsHex(userVo.getPwd().getBytes());
        userVo.setPwd(pwd);
        UserExample example = new UserExample();
        example.createCriteria().andLoginnameEqualTo(userVo.getLoginname()).andPwdEqualTo(userVo.getPwd());

        List<User> users = userMapper.selectByExample(example);
        if(users.size()>=1){
            return users.get(0);
        }else{
            return null;
        }

    }
}
