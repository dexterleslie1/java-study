package com.future.demo.chat.server.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

public class WebsocketInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            String username = servletServerHttpRequest.getServletRequest().getParameter("usernameSelf");
            if(!StringUtils.isEmpty(username)) {
                username = URLDecoder.decode(username, "utf-8");
                attributes.put("usernameSelf", username);
            }

            String clientIp = getIpAddr(servletServerHttpRequest.getServletRequest());
            if (!StringUtils.isEmpty(clientIp)) {
                attributes.put("clientIp", clientIp);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    public String getIpAddr(HttpServletRequest request) {
        String[] tryHeads={
                "x-forwarded-for",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR",
                "X-Real-IP"
        };
        String ip=null;
        for(String s:tryHeads){
            ip = request.getHeader(s);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if (ip.indexOf(",") != -1) ip = ip.split(",")[0];
                break;
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
    }
}
