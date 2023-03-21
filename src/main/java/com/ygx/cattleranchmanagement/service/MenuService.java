package com.ygx.cattleranchmanagement.service;

import com.ygx.cattleranchmanagement.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import utils.result.Result;

import java.util.List;

/**
 * 路由表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 15:53:05
 */
public interface MenuService {

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    Result<?> queryAll();

    /**
     * 通过用户id查询菜单
     *
     * @param userId 用户id
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> queryMenuByUserId(Long userId);
}
