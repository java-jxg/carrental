package com.car.sys.service.impl;

import com.car.sys.domain.User;
import com.car.sys.mapper.MyMapper;
import com.car.sys.service.UserService;
import com.car.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MyMapper myMapper;
    @Override
    public User login(UserVo userVo) {
        String pwd = DigestUtils.md5DigestAsHex(userVo.getPwd().getBytes());
        userVo.setPwd(pwd);
        return myMapper.login(userVo);
    }
}
