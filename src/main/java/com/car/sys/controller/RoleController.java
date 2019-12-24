package com.car.sys.controller;


import com.car.sys.service.RoleService;
import com.car.sys.utils.*;

import com.car.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.car.sys.utils.ResultObj.DISPATCH_ERROR;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        return roleService.queryAllRole(roleVo);
    }
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVo roleVo){
        try{
            roleService.addRole(roleVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVo roleVo){
        try{
            roleService.updateRole(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("deleteRole")
    public ResultObj deleteRole(RoleVo roleVo){
        try{
            roleService.deleteRole(roleVo.getRoleid());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }
    @RequestMapping("deleteBatchRole")
    public ResultObj deleteBatchRole(RoleVo roleVo){
        try{
            roleService.deleteBatchRole(roleVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }



    @RequestMapping("initRoleMenuTreeJson")
    public DataGridView initRoleMenuTreeJson(RoleVo roleVo){
        return roleService.initRoleMenuTreeJson(roleVo.getRoleid());
    }

    @RequestMapping("saveRoleMenu")
    public ResultObj saveRoleMenu(RoleVo roleVo) {
        try {
            roleService.saveRoleMenu(roleVo);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

}
