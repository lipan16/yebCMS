package com.lx.yeb.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket服务器
 * ws://localhost:8080/websocket/
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{uid}")
public class MyWebSocket{

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException{
        session.getBasicRemote().sendText(uid + ", 你好，欢迎连接WebSocket");
    }

    @OnClose
    public void onClose(){
        log.info(this + " 关闭连接");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText("发送消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        log.error("发生错误", throwable);
        throwable.printStackTrace();
    }
}
