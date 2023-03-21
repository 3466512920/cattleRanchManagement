package com.ygx.cattleranchmanagement.dao;

import com.ygx.cattleranchmanagement.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户角色表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-08 22:51:21
 */
public interface UserRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserRole queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param userRole 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<UserRole> queryAllByLimit(UserRole userRole, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param userRole 查询条件
     * @return 总行数
     */
    long count(UserRole userRole);

    /**
     * 新增数据
     *
     * @param userRole 实例对象
     * @return 影响行数
     */
    int insert(UserRole userRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserRole> entities);

    /**
     * 修改数据
     *
     * @param userRole 实例对象
     * @return 影响行数
     */
    int update(UserRole userRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询用户id
     *
     * @return {@link List}<{@link UserRole}>
     */
    List<UserRole> queryByUserId(Long userId);
}

