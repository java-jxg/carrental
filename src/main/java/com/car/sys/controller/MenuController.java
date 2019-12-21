package com.car.sys.controller;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.Menu;
import com.car.sys.domain.User;
import com.car.sys.service.MenuService;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.TreeNode;
import com.car.sys.utils.TreeNodeBuilder;
import com.car.sys.utils.WebUtils;
import com.car.sys.vo.MenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 树型结构展示左侧
     * @param menuVo
     * @return
     */
    @RequestMapping("loadIndexleftMenuJson")
    public List<TreeNode> loadIndexLeftMenuJson(MenuVo menuVo){
        //得到当前登陆的用户对象
        User user =(User) WebUtils.getHttpSession().getAttribute("user");

        List<Menu> list = null;
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);//只查询可用的
        if(user.getType()==SysConstast.USER_TYPE_SUPER){
            list = menuService.queryAllMenuForList(menuVo);
        }else {
            list = menuService.queryMenuByIdForList(menuVo,user.getUserid());
        }
        List<TreeNode> nodes = getNodes(list);
        return TreeNodeBuilder.builder(nodes,1);
    }

    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(MenuVo menuVo){
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
        List<Menu> menus = menuService.queryAllMenuForList(menuVo);
        List<TreeNode> nodes = getNodes(menus);
        return new DataGridView(nodes);

    }
    public List<TreeNode> getNodes(List<Menu> menus){
        List<TreeNode> nodes = new ArrayList<> ();
        for (Menu menu: menus) {
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            String icon = menu.getIcon();
            String href = menu.getHref();
            Boolean spread = menu.getSpread()==SysConstast.SPREAD_TRUE?true:false;
            String target = menu.getTarget();
            nodes.add(new TreeNode(id,pid,title,icon,href,spread,target));
        }
        return nodes;
    }
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(MenuVo menuVo){
        return menuService.queryAllMenu(menuVo);
    }
    @RequestMapping("addMenu")
    public void addMenu(MenuVo menuVo){
        menuService.addMenu(menuVo);
    }

    @RequestMapping("updateMenu")
    public void updateMenu(MenuVo menuVo){
        menuService.updateMenu(menuVo);
    }

    @RequestMapping("deleteMenu")
    public void deleteMenu(@RequestParam("id")Integer id){
        menuService.deleteMenu(id);
    }

}
