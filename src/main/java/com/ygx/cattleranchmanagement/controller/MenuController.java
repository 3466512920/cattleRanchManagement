package com.ygx.cattleranchmanagement.controller;

import com.ygx.cattleranchmanagement.entity.Menu;
import com.ygx.cattleranchmanagement.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.result.Result;

import javax.annotation.Resource;

/**
 * 路由表(Menu)表控制层
 *
 * @author makejava
 * @since 2023-03-13 15:53:05
 */
@Api(tags = {"菜单接口列表"})
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 得到所有菜单
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("查询所有菜单")
    @GetMapping("/queryAllMenu")
    public Result<?> getAllMenu(){
        return this.menuService.queryAll();
    }



}

