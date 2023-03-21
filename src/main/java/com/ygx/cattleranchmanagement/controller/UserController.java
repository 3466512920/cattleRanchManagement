package com.ygx.cattleranchmanagement.controller;

import com.ygx.cattleranchmanagement.entity.User;
import com.ygx.cattleranchmanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import utils.paginationQuery.SendPageQueryData;
import utils.result.Result;
import utils.userUtils.LoginInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2023-03-07 15:00:16
 */
@Api(tags = {"用户接口列表"})
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 查询通过id
     *
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("通过用户ID查询用户")
    @GetMapping("/{userId}")
    public Result<?> queryById(@PathVariable("userId") Long userId){
        return this.userService.queryById(userId);
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public Result<?> addUser(@RequestBody User user){
        return this.userService.addUser(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("更新用户信息")
    @PutMapping("/updateUser")
    public Result<?> updateUser(@RequestBody User user){
        return this.userService.update(user);
    }

    /**
     * 删除通过id
     *
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/{userId}")
    public Result<?> deleteById(@PathVariable("userId") Long userId){
        return this.userService.deleteById(userId);
    }

    /**
     * 获得一个新账户
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("获取一个新账号")
    @GetMapping("/getAccount")
    public Result<?> getAccount(){
        return this.userService.getAccount();
    }

    /**
     * 登录
     *
     * @param loginInfo 登录信息
     * @return {@link Result}
     */
    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginInfo loginInfo){
       return this.userService.login(loginInfo);
    }

    /**
     * 根据token从redis数据库获取用户信息
     *
     * @param token 令牌
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @ApiOperation("通过token获取用户信息")
    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam("token") String token){
        return this.userService.getUserInfo(token);
    }


    /**
     * 注销:从redis中删除token以及用户信息
     *
     * @param token 令牌
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("退出登陆")
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        return this.userService.logout(token);
    }

    /**
     * 查询所有
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("查询所有用户")
    @GetMapping("/queryAll")
    public Result<?> queryAll(){
       return this.userService.queryAll();
    }

    /**
     * 按名称查询
     *
     * @param name 名字
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("通过用户名查询用户")
    @GetMapping("/queryByName")
    public Result<?> queryByName(@RequestParam(value="name", required = false) String name) {
        return this.userService.queryByName(name);
    }


}

