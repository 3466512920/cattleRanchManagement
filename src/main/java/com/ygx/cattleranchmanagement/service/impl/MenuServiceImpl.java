package com.ygx.cattleranchmanagement.service.impl;

import com.ygx.cattleranchmanagement.dao.RoleMenuDao;
import com.ygx.cattleranchmanagement.dao.UserRoleDao;
import com.ygx.cattleranchmanagement.entity.Menu;
import com.ygx.cattleranchmanagement.dao.MenuDao;
import com.ygx.cattleranchmanagement.entity.RoleMenu;
import com.ygx.cattleranchmanagement.entity.UserRole;
import com.ygx.cattleranchmanagement.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import utils.result.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 路由表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 15:53:05
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> queryAll() {
        List<Menu> menuList = this.menuDao.queryFirstMenu();
        this.queryChildMenu(menuList);
        return Result.success(menuList);
    }

    /**
     * 通过用户id查询菜单
     *
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     */
    public List<Menu> queryMenuByUserId(Long userId){
        // 查询一级菜单
        List<Menu> menuList = this.queryChildMenu(userId, 0);
        // 递归查询所有子菜单
        this.queryAllChildMenu(userId, menuList);
        return menuList;
    }

    public void queryChildMenu(List<Menu> menuList){
        if(menuList != null){
            // 获取每一个一级菜单的子菜单
            for(Menu menu : menuList){
                List<Menu> childMenu = this.menuDao.queryByFirstMenuId(menu.getMenuId());
                menu.setChildren(childMenu);

                // 多级菜单时可递归
                queryChildMenu(childMenu);
            }
        }
    }

    public List<Menu> queryChildMenu(Long userId, Integer pid){
        List<Menu> menuList = new ArrayList<>();
        // 查询用户角色表
        List<UserRole> userRoleList = this.userRoleDao.queryByUserId(userId);
        for(UserRole userRole : userRoleList){
            // 查询角色菜单表
            List<RoleMenu> roleMenuList = this.roleMenuDao.queryByRoleId(userRole.getRoleId());
            for(RoleMenu roleMenu : roleMenuList){
                // 查询菜单表
                Menu menu = this.menuDao.queryById(roleMenu.getMenuId());
                if(Objects.equals(menu.getParentId(), pid)){
                    menuList.add(menu);
                }
            }
        }
        return menuList;
    }

    public void queryAllChildMenu(Long userId, List<Menu> menuList){
        if(menuList != null){
            for(Menu menu : menuList){
                List<Menu> childMenus = this.queryChildMenu(userId, menu.getMenuId());
                menu.setChildren(childMenus);
                // 递归查询所有子菜单
                queryAllChildMenu(userId, childMenus);
            }
        }
    }

}
