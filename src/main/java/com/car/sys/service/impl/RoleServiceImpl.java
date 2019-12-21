package com.car.sys.service.impl;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.*;
import com.car.sys.mapper.RoleMapper;
import com.car.sys.mapper.RoleMenuMapper;
import com.car.sys.mapper.RoleUserMapper;
import com.car.sys.service.MenuService;
import com.car.sys.service.RoleService;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.TreeNode;
import com.car.sys.vo.MenuVo;
import com.car.sys.vo.RoleVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private MenuService menuService;

    @Override
    public List<Role> queryAllRoleForList(RoleVo roleVo) {
        RoleExample example = new RoleExample ();
        example.createCriteria();
        List<Role> roles = roleMapper.selectByExample (example);
        return roles;
    }

    @Override
    public List<Role> queryRoleByIdForList(RoleVo roleVo, Integer userId) {
        return null;
    }

    @Override
    public DataGridView queryAllRole(RoleVo roleVo) {
        RoleExample example = new RoleExample ();
        RoleExample.Criteria criteria = example.createCriteria();
        if(roleVo.getRolename()!=null){
            criteria.andRolenameLike("%"+roleVo.getRolename()+"%");
        }
        if(roleVo.getRoledesc()!=null){
            criteria.andRoledescLike("%"+roleVo.getRoledesc()+"%");
        }
        if(roleVo.getAvailable()!=null){
            criteria.andAvailableEqualTo(roleVo.getAvailable());
        }
        Page<Object> page = PageHelper.startPage(roleVo.getPage(),roleVo.getLimit());
        List<Role> roles = roleMapper.selectByExample (example);
        return new DataGridView(page.getTotal(),roles);
    }

    @Override
    public void addRole(RoleVo roleVo) {
        if(roleVo.getRoleid()!=null){
            roleVo.setRoleid(null);
        }
        roleMapper.insert(roleVo);
    }

    @Override
    public void updateRole(RoleVo roleVo) {
        roleMapper.updateByPrimaryKey(roleVo);
    }

    @Override
    public void deleteRole(Integer id) {
        //删除role
        roleMapper.deleteByPrimaryKey(id);
        //删除role_menu
        RoleMenuExample roleMenuExample = new RoleMenuExample();
        roleMenuExample.createCriteria().andRidEqualTo(id);
        roleMenuMapper.deleteByExample(roleMenuExample);
        //删除role_user
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRidEqualTo(id);
        roleUserMapper.deleteByExample(roleUserExample);
    }

    @Override
    public void deleteBatchRole(Integer[] ids) {
        for (Integer id:ids){
            deleteRole(id);
        }
    }

    @Override
    public DataGridView initRoleMenuTreeJson(Integer roleid) {
        //查询本权限所有列表
        RoleMenuExample example = new RoleMenuExample();
        example.createCriteria().andRidEqualTo(roleid);
        List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(example);
        //所有列表
        MenuVo menuVo = new MenuVo();
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
        List<Menu> menus = menuService.queryAllMenuForList(menuVo);
        List<TreeNode> nodes = new ArrayList<>();
        for (Menu menu: menus) {
            String  checkArr = SysConstast.CODE_ZERO+"";
            for(RoleMenuKey r : roleMenuKeys){
                if(r.getMid()==menu.getId()){
                    checkArr = SysConstast.CODE_ONE+"";
                }
            }
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            Boolean spread = menu.getSpread()==SysConstast.SPREAD_TRUE?true:false;

            nodes.add(new TreeNode(id,pid,title,spread,checkArr));
        }
        return new DataGridView(nodes);
    }
}
