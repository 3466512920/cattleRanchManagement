package com.ygx.cattleranchmanagement.dao;

import com.ygx.cattleranchmanagement.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 路由表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 15:53:05
 */
public interface MenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    Menu queryById(Integer menuId);

    /**
     * 查询指定行数据
     *
     * @param menu     查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Menu> queryAllByLimit(Menu menu, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param menu 查询条件
     * @return 总行数
     */
    long count(Menu menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int insert(Menu menu);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Menu> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Menu> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Menu> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Menu> entities);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(Integer menuId);

    /**
     * 查询所有
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> queryAll();

    /**
     * 查询第一级菜单
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> queryFirstMenu();

    /**
     * 首先查询菜单id
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> queryByFirstMenuId(Integer menuId);
}

