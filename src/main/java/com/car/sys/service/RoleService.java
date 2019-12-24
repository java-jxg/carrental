package com.car.sys.service;

import com.car.sys.domain.Role;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.RoleVo;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有角色
     */
    public List<Role> queryAllRoleForList(RoleVo roleVo);

    /**
     * 普通用户根据id查询
     */
    public List<Role> queryRoleByIdForList(RoleVo roleVo, Integer userId);

    /**
     * 分页查询
     */
    public DataGridView queryAllRole(RoleVo roleVo);

    /**
     * 添加角色
     * @param roleVo
     */
    void addRole(RoleVo roleVo);

    void updateRole(RoleVo roleVo);

    void deleteRole(Integer id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatchRole(Integer[] ids);

    public DataGridView initRoleMenuTreeJson(Integer roleid);

    void saveRoleMenu(RoleVo roleVo);

}

