<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/18
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<button onclick="sendMes" value="发送"/>


<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    var wsServer = null;
    var ws = null;
    wsServer = "ws://localhost:8080/yike/ws";
    ws = new WebSocket(wsServer); //创建WebSocket对象
    ws.onopen = function(evt) {
        layer.msg("已经建立连接", {
            offset : 0
        });
    };
    ws.onmessage = function(evt) {
        var message = JSON.parse(evt.data);//将数据解析成JSON形式
        showMess(message);//展示消息
    };
    ws.onerror = function(evt) {
        layer.msg("产生异常", {
            offset : 0
        });
    };
    ws.onclose = function(evt) {
        layer.msg("已经关闭连接", {
            offset : 0
        });
    };

</script>
<script>
    function sendMes() {
        var data = {"name": "徐悦然"};
        ws.send(JSON.stringify(data));
    }
</script>
</body>
</html>
