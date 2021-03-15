package com.lx.yeb.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket服务器
 * ws://localhost:8080/websocket/
 */
@Component
@ServerEndpoint("/websocket/{uid}")
public class MyWebSocket{
    Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException{
        session.getBasicRemote().sendText(uid + ", 你好，欢迎连接WebSocket");
    }

    @OnClose
    public void onClose(){
        logger.info(this + " 关闭连接");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException{
        session.getBasicRemote().sendText("发送消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.error("发生错误", throwable);
        throwable.printStackTrace();
    }
}
