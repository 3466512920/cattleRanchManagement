package com.ygx.cattleranchmanagement.service.impl;

import com.ygx.cattleranchmanagement.dao.MenuDao;
import com.ygx.cattleranchmanagement.dao.RoleMenuDao;
import com.ygx.cattleranchmanagement.entity.Role;
import com.ygx.cattleranchmanagement.dao.RoleDao;
import com.ygx.cattleranchmanagement.entity.RoleMenu;
import com.ygx.cattleranchmanagement.entity.User;
import com.ygx.cattleranchmanagement.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import utils.paginationQuery.SendPageQueryData;
import utils.result.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 00:02:31
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private MenuDao menuDao;

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Result<?> insert(Role role) {

        //写入角色表
        role.setDeleted(0);
        int mark = this.roleDao.insert(role);

        // 写入角色关系表
        int k = 0;
        if (role.getMenuIdList() != null) {
            for (Integer menuId : role.getMenuIdList()) {
                k += this.roleMenuDao.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
            if (mark != 1 || k != role.getMenuIdList().size()) {
                return Result.fail("添加失败");
            }
        }
        return Result.success("添加成功");

    }


    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public Result<?> queryById(Integer roleId) {
        Role role = this.roleDao.queryById(roleId);
        if(role != null) {
            // 查询当前角色菜单列表
            List<RoleMenu> roleMenuList = this.roleMenuDao.queryByRoleId(roleId);
            List<Integer> returnMenu = new ArrayList<>();
            for(RoleMenu roleMenu : roleMenuList){
                String isLeaf = this.menuDao.queryById(roleMenu.getMenuId()).getIsLeaf();
                if(Objects.equals(isLeaf, "Y")){
                    returnMenu.add(roleMenu.getMenuId());
                }
            }
            role.setMenuIdList(returnMenu);
            return Result.success(role);
        }else{
            return Result.fail();
        }
    }

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> queryAll() {
        List<Role> roleList = this.roleDao.queryAll();
        if(roleList != null){
            SendPageQueryData<Role> sendPageQueryData = new SendPageQueryData<>();
            sendPageQueryData.setDataList(roleList);
            sendPageQueryData.setDataVolume(roleList.size());
            return Result.success(sendPageQueryData);
        }else{
            return Result.fail();
        }
    }

    /**
     * 按名称查询
     *
     * @param roleName 角色名
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> queryByName(String roleName) {
        //模糊查询
        String likeName = "%%"+roleName+"%%";
        List<Role> roleList = this.roleDao.queryByName(likeName);
        if (roleList != null) {
            SendPageQueryData<Role> sendPageQueryData = new SendPageQueryData<>();
            sendPageQueryData.setDataList(roleList);
            sendPageQueryData.setDataVolume(roleList.size());
            return Result.success(sendPageQueryData);
        } else {
            return Result.fail("该角色不存在");
        }
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Result<?> update(Role role) {

        // 角色信息和权限更新：涉及role和role_menu两张表
        // role正常更新
        // role_menu更新思路：删除原有权限，新增新权限

        // 更新角色表
        int mark = this.roleDao.update(role);

        // 查询角色原有菜单列表
        List<RoleMenu> roleMenuList = this.roleMenuDao.queryByRoleId(role.getRoleId());

        int before = 0;
        int now = 0;
        if (roleMenuList != null) {
            // 删除原有权限
            for (RoleMenu roleMenu : roleMenuList) {
                before += this.roleMenuDao.deleteById(roleMenu.getId());
            }
        }
        // 插入新权限
        for (Integer menuId : role.getMenuIdList()) {
            now += this.roleMenuDao.insert(new RoleMenu(null, role.getRoleId(), menuId));
        }
        if (mark == 1 && before == Objects.requireNonNull(roleMenuList).size() && now == role.getMenuIdList().size()){
            return Result.success("更新成功");
        }else{
            return Result.fail("更新失败");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public Result<?> deleteById(Integer roleId) {
        // 删除角色表数据
        this.roleDao.deleteById(roleId);

        // 删除角色关系表数据
        // 查询角色原有菜单列表
        List<RoleMenu> roleMenuList = this.roleMenuDao.queryByRoleId(roleId);
        if (roleMenuList != null) {
            // 删除角色菜单列表
            for (RoleMenu roleMenu : roleMenuList) {
                this.roleMenuDao.deleteById(roleMenu.getId());
            }
        }
        return Result.success("删除成功");
    }
}
