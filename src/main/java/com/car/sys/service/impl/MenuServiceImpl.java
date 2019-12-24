package com.car.sys.service.impl;

import com.car.sys.domain.*;
import com.car.sys.domain.MenuExample.Criteria;
import com.car.sys.mapper.MenuMapper;
import com.car.sys.mapper.RoleMenuMapper;
import com.car.sys.mapper.RoleUserMapper;
import com.car.sys.service.MenuService;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.MenuVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public List<Menu> queryAllMenuForList(MenuVo menuVo) {
        MenuExample example = new MenuExample ();
        Criteria criteria = example.createCriteria();
        if(menuVo.getAvailable()!=null){
            criteria.andAvailableEqualTo(menuVo.getAvailable());
        }
        List<Menu> menus = menuMapper.selectByExample (example);
        return menus;
    };

    @Override
    public List<Menu> queryMenuByIdForList(MenuVo menuVo, Integer userId) {
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andUidEqualTo(userId);
        List<RoleUserKey> roleUserKeys = roleUserMapper.selectByExample(roleUserExample);
        List<Integer> rids = new ArrayList<Integer>();
        for(RoleUserKey r : roleUserKeys){
            rids.add(r.getRid());
        }
        RoleMenuExample roleMenuExample = new RoleMenuExample();
        roleMenuExample.createCriteria().andRidIn(rids);
        List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(roleMenuExample);
        List<Integer> mids = new ArrayList<Integer>();
        for(RoleMenuKey rm : roleMenuKeys){
            mids.add(rm.getMid());
        }

        MenuExample example = new MenuExample ();
        example.createCriteria().andIdIn(mids);
        List<Menu> menus = menuMapper.selectByExample (example);
        return menus;
    }

    /**
     * 实现 A and (B OR C)
     * @param menuVo
     * @return
     */

    @Override
    public DataGridView queryAllMenu(MenuVo menuVo) {
        MenuExample example = new MenuExample ();
        Criteria criteria = example.createCriteria();
        if(menuVo.getTitle()!="" && menuVo.getTitle()!=null){
            criteria.andTitleLike("%"+menuVo.getTitle()+"%");
        }
        if(menuVo.getId()!=null){
            criteria.andIdEqualTo(menuVo.getId());
        }
        Criteria criteria1 = example.createCriteria();
        if(menuVo.getTitle()!="" && menuVo.getTitle()!=null){
            criteria1.andTitleLike("%"+menuVo.getTitle()+"%");
        }
        if(menuVo.getId()!=null){
            criteria1.andPidEqualTo(menuVo.getId());
        }
        example.or(criteria1);
        Page<Object> page = PageHelper.startPage(menuVo.getPage(),menuVo.getLimit());
        List<Menu> menus = menuMapper.selectByExample (example);
        return new DataGridView(page.getTotal(),menus);
    }

    @Override
    public void addMenu(MenuVo menuVo) {
        if(menuVo.getId()!=null){
            menuVo.setId(null);
        }
        menuMapper.insert(menuVo);
    }

    @Override
    public void updateMenu(MenuVo menuVo) {
        menuMapper.updateByPrimaryKey(menuVo);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
        //同时删除role_menu表
        RoleMenuExample example = new  RoleMenuExample();
        example.createCriteria().andMidEqualTo(id);
        roleMenuMapper.deleteByExample(example);
    }

    @Override
    public Integer checkMenuHasChildren(Integer id) {
        MenuExample example = new MenuExample ();
        Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(id);
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus.size();
    }

}
