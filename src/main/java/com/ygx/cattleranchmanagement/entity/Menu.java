package com.ygx.cattleranchmanagement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路由表(Menu)实体类
 *
 * @author makejava
 * @since 2023-03-13 15:56:22
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 102719804605627093L;
    /**
     * 菜单ID
     */
    private Integer menuId;

    private String component;
    /**
     * 路径
     */
    private String path;
    /**
     * 重定向路径
     */
    private String redirect;
    /**
     * 菜单名字
     */
    private String name;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 父菜单ID
     */
    private Integer parentId;
    /**
     * 是否为叶子菜单
     */
    private String isLeaf;
    /**
     * 是否隐藏
     */
    private Integer hidden;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children;

    @TableField(exist = false)
    private Map<String, Object> meta;
    public Map<String, Object> getMeta(){
        meta = new HashMap<>();
        meta.put("title", title);
        meta.put("icon", icon);
        return meta;
    }
}

