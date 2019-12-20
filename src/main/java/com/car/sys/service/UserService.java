package com.car.sys.service;

import com.car.sys.domain.User;
import com.car.sys.vo.UserVo;

public interface UserService {
    User login(UserVo userVo);
}
