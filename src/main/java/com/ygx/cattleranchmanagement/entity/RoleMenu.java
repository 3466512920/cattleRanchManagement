package com.ygx.cattleranchmanagement.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单关系表(RoleMenu)实体类
 *
 * @author makejava
 * @since 2023-03-13 17:45:38
 */
@Data
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 606625488001216451L;

    private Integer id;

    private Integer roleId;

    private Integer menuId;

    public RoleMenu(Object o, Integer roleId, Integer menuId) {
        this.id = (Integer) o;
        this.roleId = roleId;
        this.menuId = menuId;
    }
}

