package com.car.sys.service;

import com.car.sys.domain.Menu;
import com.car.sys.vo.MenuVo;

import java.util.List;

public interface MenuService {
    /**
     * 查询所有菜单
     */
    public List<Menu> queryAllMenuForList(MenuVo menuVo);

    /**
     * 普通用户根据id查询
     */
    public List<Menu> queryMenuByIdForList(MenuVo menuVo,Integer userId);
}
