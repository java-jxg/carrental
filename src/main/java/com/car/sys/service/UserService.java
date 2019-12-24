package com.car.sys.service;

import com.car.sys.domain.User;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.UserVo;

import java.util.List;

public interface UserService {
    User login(UserVo userVo);

    DataGridView queryAllUser(UserVo uservo);

    void addUser(UserVo uservo);

    void updateUser(UserVo uservo);

    void deleteBatchRole(Integer[] ids);

    void deleteUser(Integer userid);

    void resetUserPwd(Integer userid);

    DataGridView initUserRole(UserVo uservo);

    void saveUserRole(UserVo userVo);
}
