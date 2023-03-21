package com.ygx.cattleranchmanagement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表(Role)实体类
 *
 * @author makejava
 * @since 2023-03-13 18:01:45
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 314414789228158091L;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 是否删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<Integer> menuIdList;

}

