package com.car.sys.mapper;

import com.car.sys.domain.User;
import com.car.sys.domain.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMapper {
    User login(User user);
    void deleteRoleMenu(Integer mid);
}