package com.ygx.cattleranchmanagement.controller;

import com.ygx.cattleranchmanagement.entity.Role;
import com.ygx.cattleranchmanagement.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.result.Result;

import javax.annotation.Resource;

/**
 * 角色表(Role)表控制层
 *
 * @author makejava
 * @since 2023-03-13 00:02:31
 */
@Api(tags = {"角色接口列表"})
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 查询通过id
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("通过ID查询角色")
    @GetMapping("/{roleId}")
    public Result<?> queryById(@PathVariable("roleId") Integer roleId){
        return this.roleService.queryById(roleId);
    }

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("查询所有角色")
    @GetMapping("/queryAll")
    public Result<?> queryAll(){
        return this.roleService.queryAll();
    }

    /**
     * 按名称查询
     *
     * @param roleName 角色名
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("通过角色名查询")
    @GetMapping("/queryByName")
    public Result<?> queryByName(@RequestParam(value="roleName", required = false) String roleName) {
        return this.roleService.queryByName(roleName);
    }

    /**
     * 新增数据
     *
     * @param role 实体
     * @return 新增结果
     */
    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    public Result<?> addRole(@RequestBody Role role) {
        return this.roleService.insert(role);
    }

    /**
     * 编辑数据
     *
     * @param role 实体
     * @return 编辑结果
     */
    @ApiOperation("更新角色")
    @PutMapping("/updateRole")
    public Result<?> updateRole(@RequestBody Role role) {
        return this.roleService.update(role);
    }

    /**
     * 删除数据
     *
     * @param roleId 主键
     * @return 删除是否成功
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public Result<?> deleteById(@PathVariable("roleId") Integer roleId) {
        return this.roleService.deleteById(roleId);
    }

}

