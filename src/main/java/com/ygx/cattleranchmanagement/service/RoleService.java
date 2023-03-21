package com.ygx.cattleranchmanagement.service;

import com.ygx.cattleranchmanagement.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import utils.result.Result;

/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 00:02:31
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    Result<?> queryById(Integer roleId);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Result<?> insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Result<?> update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    Result<?> deleteById(Integer roleId);

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    Result<?> queryAll();

    /**
     * 按名称查询
     *
     * @param roleName 角色名
     * @return {@link Result}<{@link ?}>
     */
    Result<?> queryByName(String roleName);
}
