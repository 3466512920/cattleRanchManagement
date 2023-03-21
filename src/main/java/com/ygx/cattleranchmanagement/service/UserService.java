package com.ygx.cattleranchmanagement.service;

import com.ygx.cattleranchmanagement.entity.User;
import utils.result.Result;
import utils.userUtils.LoginInfo;

import java.util.List;
import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-03-07 15:00:29
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Result<?> queryById(Long id);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    Result<?> addUser(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    Result<?> update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Result<?> deleteById(Long id);


    /**
     * 登录
     *
     * @param loginInfo 登录信息
     * @return {@link User}
     */
    Result<?> login(LoginInfo loginInfo);

    /**
     * 获取用户信息
     *
     * @param token 令牌
     * @return {@link Map}<{@link String}, {@link Object}>
     */
     Result<?> getUserInfo(String token);

    /**
     * 注销
     *
     * @param token 令牌
     */
    Result<?> logout(String token);

    /**
     * 查询所有
     *
     * @return {@link List}<{@link User}>
     */
    Result<?> queryAll();

    /**
     * 按名称查询
     *
     * @param name 名字
     * @return {@link User}
     */
    Result<?> queryByName(String name);

    /**
     * 获得账户
     *
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getAccount();

}
