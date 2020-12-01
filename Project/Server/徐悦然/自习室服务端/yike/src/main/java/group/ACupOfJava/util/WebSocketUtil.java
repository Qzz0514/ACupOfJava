package group.ACupOfJava.util;

import group.ACupOfJava.websocket.WebSocketServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {
    // 记录当前在线连接数
    public static int onlineCount = 0;
    // 客户端，用来发送
    public static ConcurrentHashMap<Integer, WebSocketServer> webSocketMap = new ConcurrentHashMap<Integer, WebSocketServer>();


}
