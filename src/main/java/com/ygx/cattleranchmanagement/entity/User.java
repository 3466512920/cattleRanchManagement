package com.ygx.cattleranchmanagement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2023-03-12 15:22:43
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -67623094253510564L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 账号
     */
    private Long account;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 电话号码
     */
    private String telephoneNumber;
    /**
     * 注册日期
     */
    private String registerDate;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 用户状态
     */
    private String state;
    /**
     * 照片
     */
    private String photo;
    /**
     * 备注
     */
    private String note;
    /**
     * 是否删除
     */
    private Integer deleted;


    /**
     * 用户角色id列表
     */
    @TableField(exist = false)
    private List<Integer> roleIdList;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", account=" + account +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", registerDate=" + registerDate +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", photo='" + photo + '\'' +
                ", note='" + note + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}

