package com.car.sys.service;

import com.car.sys.domain.Menu;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.MenuVo;

import javax.xml.crypto.Data;
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

    /**
     * 分页查询
     */
    public DataGridView queryAllMenu(MenuVo menuVo);

    void addMenu(MenuVo menuVo);

    void updateMenu(MenuVo menuVo);

    void deleteMenu(Integer id);

    Integer checkMenuHasChildren(Integer id);
}

