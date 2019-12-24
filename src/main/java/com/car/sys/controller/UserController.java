package com.car.sys.controller;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.Role;
import com.car.sys.domain.User;
import com.car.sys.service.RoleService;
import com.car.sys.service.UserService;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.ResultObj;
import com.car.sys.vo.MenuVo;
import com.car.sys.vo.RoleVo;
import com.car.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo uservo){
        return userService.queryAllUser(uservo);
    }
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo uservo){
        try{
            if(uservo.getUserid()!=null){
                uservo.setUserid(null);
            }
            uservo.setType(SysConstast.USER_TYPE_NORMAL);
            String pwd = DigestUtils.md5DigestAsHex(SysConstast.USER_DEFAULT_PWD.getBytes());
            uservo.setPwd(pwd);
            userService.addUser(uservo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo uservo){
        try{
            userService.updateUser(uservo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteBatchUser")
    public ResultObj deleteBatchUser(UserVo uservo){
        try{
            userService.deleteBatchRole(uservo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("deleteUser")
    public ResultObj deleteUser(UserVo uservo){
        try{
            userService.deleteUser(uservo.getUserid());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("resetUserPwd")
    public ResultObj resetUserPwd(Integer userid){
        try{
            userService.resetUserPwd(userid);
            return ResultObj.RESET_SUCCESS;
        }catch (Exception e){
            return ResultObj.RESET_ERROR;
        }
    }

    @RequestMapping("initUserRole")
    public DataGridView initUserRole(UserVo userVo){
        return userService.initUserRole(userVo);
    }

    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(UserVo userVo){
        try{
            userService.saveUserRole(userVo);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }
}
