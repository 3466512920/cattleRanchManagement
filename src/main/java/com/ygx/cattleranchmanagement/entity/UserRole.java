package com.ygx.cattleranchmanagement.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色表(UserRole)实体类
 *
 * @author makejava
 * @since 2023-03-13 17:58:58
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 948802080167467928L;
    /**
     * 用户角色关系ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Integer roleId;

    public UserRole(Object o, Long userId, Integer roleId) {
        this.id = (Integer) o;
        this.userId = userId;
        this.roleId = roleId;
    }

}

