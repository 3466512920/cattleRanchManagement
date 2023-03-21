package com.ygx.cattleranchmanagement.config;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Websocket服务
 *
 * @author SeptMeteor
 * 注意在websocket通信中只能传string
 * @date 2023-02-21 05:40:27
 */
@Component
@ServerEndpoint("/socket/{account}")
public class WebSocketServer {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static final ConcurrentHashMap<String,WebSocketServer>  webSocketList = new ConcurrentHashMap<>();
    //静态变量：记录当前在线连接数
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //此处是解决无法注入的关键
    private static ApplicationContext applicationContext;
    //要注入的service或者dao
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /***
     * 把登录用户存到sessionMap中
     *
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "account") String account, Session session) {
        //在线数加1
        addOnlineCount();
        System.out.println("建立第" + getOnlineCount() + "个连接。。。");
        System.out.println("用户"+ account);
        this.session = session;
        //加入set中
        webSocketList.put(account,this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "account") String account) {
        System.out.println("用户" + account + "断开连接。。。");
        webSocketList.remove(this);
        subOnlineCount();
        System.out.println("当前连接数：" + getOnlineCount());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put("message", message);
        System.out.println("来自客户端的消息:" + jo.toString());
//        AppMsg appMsg = JSONObject.parseObject(jo.toString(), AppMsg.class);
        sendInfo(jo.toString());
    }

    private String setUserList(){
        ArrayList<String> list = new ArrayList<>(webSocketList.keySet());
        Message message = new Message();
        message.setUerIDs(list);
        return JSON.toJSONString(message);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (String key : webSocketList.keySet()) {
            try {
                JSONObject jo = new JSONObject();
                jo.put("message", message);
                System.out.println("有用户登陆了！");
                webSocketList.get(key).sendMessage(jo.toString());
            } catch (IOException e) {
                continue;
            }
        }
    }

    //服务端发送消息给指定客户端
    public void sendMessage(String message) throws IOException {
        try {
            this.session.getBasicRemote().sendText(message);
            System.out.println("发送出了新消息了");
        }catch (Exception e){
            webSocketList.remove(this);
        }
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 单发自定义消息
     * userId 给谁发
     * */
    public static void sendToUser(String message) throws IOException {
        for (String key : webSocketList.keySet()) {
            //发送消息给管理员
            if(key.equals("2023000001")){
                try {
                    webSocketList.get(key).sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
