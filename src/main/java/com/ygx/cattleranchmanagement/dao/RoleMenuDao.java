package com.ygx.cattleranchmanagement.dao;

import com.ygx.cattleranchmanagement.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 角色菜单关系表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 17:45:38
 */
public interface RoleMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleMenu queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param roleMenu 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<RoleMenu> queryAllByLimit(RoleMenu roleMenu, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param roleMenu 查询条件
     * @return 总行数
     */
    long count(RoleMenu roleMenu);

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int insert(RoleMenu roleMenu);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RoleMenu> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RoleMenu> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RoleMenu> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RoleMenu> entities);

    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int update(RoleMenu roleMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询角色id
     *
     * @return {@link List}<{@link RoleMenu}>
     */
    List<RoleMenu> queryByRoleId(Integer roleId);
}

