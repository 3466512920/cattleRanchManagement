package com.ygx.cattleranchmanagement.config;

import lombok.Data;

import java.util.List;


/**
 * 消息
 *
 * @author SeptMeteor
 * @date 2023-02-21 05:41:09
 */
@Data
public class Message {
//    时间
    private String time;
//    接收方
    private String to;
//    发送方
    private String from;
//    消息
    private String msg;
//    登录用户名
    private List<String> uerIDs;
}
