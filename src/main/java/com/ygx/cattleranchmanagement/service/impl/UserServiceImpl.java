package com.ygx.cattleranchmanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.ygx.cattleranchmanagement.dao.*;
import com.ygx.cattleranchmanagement.entity.*;
import com.ygx.cattleranchmanagement.service.MenuService;
import com.ygx.cattleranchmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ygx.cattleranchmanagement.config.JwtUtil;
import org.springframework.transaction.annotation.Transactional;
import utils.paginationQuery.SendPageQueryData;
import utils.result.Result;
import utils.userUtils.LoginInfo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-07 15:00:30
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private MenuService menuService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Result<?> queryById(Long id) {
        // 查询用户信息
        User user = this.userDao.queryById(id);
        // 查询用户角色列表
        List<UserRole> userRoleList = this.userRoleDao.queryByUserId(id);
        if(userRoleList != null){
            List<Integer> roleIdList = new ArrayList<>();
            for(UserRole userRole : userRoleList){
                roleIdList.add(userRole.getRoleId());
            }
            user.setRoleIdList(roleIdList);
        }
        if(user != null){
            return Result.success(user);
        }else{
            return Result.fail("用户不存在");
        }
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @Override
    @Transactional
    public Result<?> addUser(User user) {
        // 第一步：写入用户表user
        // 第二步：写入用户决角色表user_role
        // 加密登陆密码
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // 记录注册日期
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String date = localDate+ " " + localTime;
        user.setRegisterDate(date);
        // 设置该用户是否被删除，0表示用户存在，1表示用户已删除
        user.setDeleted(0);
        int mark = this.userDao.insert(user);
        // 写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        int num = 0;
        if(roleIdList != null){
            for(Integer roleId : roleIdList) {
                num += this.userRoleDao.insert(new UserRole(null, user.getUserId(), roleId));
            }
        }
        if(mark == 1 && num == Objects.requireNonNull(roleIdList).size()){
            return Result.success("添加成功");
        }else{
            return Result.fail("添加失败");
        }
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Result<?> update(User user) {
        // 密码不空时加密
//        if(user.getPassword() != null){
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        // 更新用户信息
        int mark = this.userDao.update(user);
        // 删除用户原有角色
        List<UserRole> userRoleList = this.userRoleDao.queryByUserId(user.getUserId());
        for(UserRole userRole : userRoleList){
            this.userRoleDao.deleteById(userRole.getId());
        }
        // 新增角色
        for(Integer roleId : user.getRoleIdList()){
            this.userRoleDao.insert(new UserRole(null, user.getUserId(), roleId));
        }
        if(mark == 1){
            return Result.success("更新成功");
        }else{
            return Result.fail("更新失败");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public Result<?> deleteById(Long id) {
        // 删除用户信息
        boolean mark = this.userDao.deleteById(id) > 0;
        // 删除该用户角色信息
        List<UserRole> userRoleList = this.userRoleDao.queryByUserId(id);
        if(userRoleList != null){
            for(UserRole userRole : userRoleList){
                this.userRoleDao.deleteById(userRole.getId());
            }
        }
        if(mark){
            return Result.success("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }

    /**
     * 登录
     *
     * @param loginInfo 登录信息
     * @return {@link User}
     */
    @Override
    public Result<?> login(LoginInfo loginInfo) {
        User user = this.userDao.login(loginInfo.getAccount());

        if(user == null){
            return Result.fail("该用户不存在");
        }else{
            if(passwordEncoder.matches(loginInfo.getPassword(), user.getPassword())){

                // 方法一
                // 生成随机token,暂时使用UUID，终极方案为jwt
                // String token = "user"+ UUID.randomUUID();
                // 存入redis
                // redisTemplate.opsForValue().set(token, user, 30, TimeUnit.MINUTES);
                // 返回数据

                // 方法二
                // 使用jwt生成token
                user.setPassword(null);
                String token = jwtUtil.createToken(user);

                Map<String,Object> data = new HashMap<>();
                data.put("token", token);
                return Result.success("欢迎登陆：" + user.getName(), data);
            }else{
                return Result.fail(20002, "密码错误");
            }
        }
    }

    /**
     * 获取用户信息
     *
     * @param token 令牌
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Result<?> getUserInfo(String token) {

        // 方法一: redis
        // 根据token获取用户信息
        // Object obj = redisTemplate.opsForValue().get(token);

        // 方法二：jwt
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(loginUser != null){

            // 方法一： redis反序列化
            // User user = JSON.parseObject(JSON.toJSONString(obj), User.class);

            Map<String, Object> data = new HashMap<>();
            data.put("account", loginUser.getAccount());
            data.put("avatar", loginUser.getPhoto());

            // 角色列表
            // 查询该用户的角色列表
            List<UserRole> userRoleList = this.userRoleDao.queryByUserId(loginUser.getUserId());
            List<String> roleList = new ArrayList<>();
            for (UserRole userRole : userRoleList) {
                //查询每一种角色
                Role role = this.roleDao.queryById(userRole.getRoleId());
                roleList.add(role.getRoleName());
            }
            data.put("roles", roleList);

            // 查询角色菜单
            List<Menu> menuList = this.menuService.queryMenuByUserId(loginUser.getUserId());
            data.put("menuList", menuList);

            return Result.success(data);
        }
        return Result.fail(20003, "登陆信息无效，请重新登陆");
    }

    /**
     * 注销
     *
     * @param token 令牌
     */
    @Override
    public Result<?> logout(String token) {
        // redisTemplate.delete(token);
        return Result.success("退出登陆");
    }

    /**
     * 查询所有
     *
     * @return {@link List}<{@link User}>
     */
    @Override
    public Result<?> queryAll() {
        List<User> userList = this.userDao.queryAll();
        if(userList != null){
            //包装返回数据
            SendPageQueryData<User> sendPageQueryData = new SendPageQueryData<>();
            sendPageQueryData.setDataList(userList);
            sendPageQueryData.setDataVolume(userList.size());
            return Result.success(sendPageQueryData);
        }else{
            return Result.fail();
        }
    }

    /**
     * 按名称查询
     *
     * @param name 名字
     * @return {@link User}
     */
    @Override
    public Result<?> queryByName(String name) {
        //模糊查询
        String likeName = "%%"+name+"%%";
        List<User> userList = this.userDao.queryByName(likeName);
        if (userList != null) {
            SendPageQueryData<User> sendPageQueryData = new SendPageQueryData<>();
            sendPageQueryData.setDataList(userList);
            sendPageQueryData.setDataVolume(userList.size());
            return Result.success(sendPageQueryData);
        } else {
            return Result.fail("该用户不存在");
        }
    }

    /**
     * 获得账户
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> getAccount() {
        // 查询数据库所有历史用户记录
        List<User> userList = this.userDao.queryHistoricalUsers();
        Long max = 0L;
        //实现用户账户自动递增
        for (User user : userList) {
            if (max < user.getAccount()) {
                max = user.getAccount();
            }
        }
        Long newAccount = max + 1;
        return Result.success(newAccount);
    }

}
