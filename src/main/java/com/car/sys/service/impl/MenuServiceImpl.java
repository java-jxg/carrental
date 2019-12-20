package com.car.sys.service.impl;

import com.car.sys.domain.Menu;
import com.car.sys.domain.MenuExample;
import com.car.sys.mapper.MenuMapper;
import com.car.sys.service.MenuService;
import com.car.sys.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryAllMenuForList(MenuVo menuVo) {
        MenuExample example = new MenuExample ();
        List<Menu> menus = menuMapper.selectByExample (example);
        return menus;
    };

    @Override
    public List<Menu> queryMenuByIdForList(MenuVo menuVo, Integer userId) {
        MenuExample example = new MenuExample ();
        List<Menu> menus = menuMapper.selectByExample (example);
        return menus;
    }
}
