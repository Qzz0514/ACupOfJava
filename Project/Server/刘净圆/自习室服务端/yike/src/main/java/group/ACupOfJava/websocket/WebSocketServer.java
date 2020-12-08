package group.ACupOfJava.websocket;

import group.ACupOfJava.util.JedisUtil;
import group.ACupOfJava.util.WebSocketUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{user_id}")
public class WebSocketServer {


	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private int userId;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * @param userId 用rest风格获取 用户ID
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("user_id") int userId){
		this.session = session;
		this.userId = userId;

		if (WebSocketUtil.webSocketMap.get(userId) != null) {
			// 已经有连接，强制下线
			WebSocketServer server = WebSocketUtil.webSocketMap.get(userId);
			try {
				server.sendMessage("异地登录");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 上线
		WebSocketUtil.webSocketMap.put(userId, this);
		addOnlineCount();           //在线数加1
		System.out.println("当前在线人数" + WebSocketUtil.onlineCount);

		// 读取离线发送信息
		Jedis jedis = JedisUtil.geyJedis();
		while (jedis.llen("receive_" + userId) > 0) {
			try {
				this.sendMessage(jedis.lpop("receive_" + userId));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		jedis.close();
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		WebSocketUtil.webSocketMap.remove(userId);  //从set中删除
		subOnlineCount();           //在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {

		JSONObject jsonObject = new JSONObject(message);
		Jedis jedis = JedisUtil.geyJedis();


		String sendId = jsonObject.getString("send_id");
		Integer targetId = Integer.parseInt(jsonObject.get("target_id").toString());
		String type = jsonObject.getString("type");
		String talk = null;


		if (type.equals("text")) {
			talk = jsonObject.getString("message");
		} else {
			// 语音
			// 存储位置
			talk = jsonObject.getString("voice");
		}

		JSONObject object = new JSONObject();
		object.put("talk", talk);
		object.put("type", type);
		object.put("time", System.currentTimeMillis());
		String sendMes = object.toString();

		WebSocketServer webSocketTest = WebSocketUtil.webSocketMap.get(targetId);
		if (webSocketTest != null) {
			webSocketTest.sendMessage(sendMes);
		} else {
			// 进入缓存等待登陆
			jedis.rpush("receive_" + targetId, sendMes);
		}

		// 存储历史聊天信息
		jedis.rpush(sendId + "_" + targetId, sendMes);
		jedis.close();


		// 存储好友列表
		Set<String> friends = jedis.smembers("friends_" + sendId);
		if (!friends.contains(targetId.toString())) {
			jedis.sadd("friends_" + sendId, targetId+"");
		}



	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
//		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return WebSocketUtil.onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketUtil.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketUtil.onlineCount--;
	}
}
