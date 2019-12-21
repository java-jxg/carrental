package com.car.sys.service.impl;


import com.car.sys.domain.Menu;
import com.car.sys.domain.MenuExample;
import com.car.sys.domain.MenuExample.Criteria;
import com.car.sys.mapper.MenuMapper;
import com.car.sys.service.MenuService;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.MenuVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

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
        MenuExample example = new MenuExample ();
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
        menuMapper.insert(menuVo);
    }

    @Override
    public void updateMenu(MenuVo menuVo) {
        menuMapper.updateByPrimaryKey(menuVo);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

}
