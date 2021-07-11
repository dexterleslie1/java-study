package com.future.demo.forwarded;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    // 打印客户信息
    @GetMapping("info")
    public String info(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        String remoteAddress = request.getRemoteAddr();
        String xForwardedFor = request.getHeader("x-forwarded-for");
        String clientIp = xForwardedFor.split(",")[0];
        builder.append("remoteAddress=" + remoteAddress + "<br/>");
        builder.append("x-forwarded-for=" + xForwardedFor + "<br/>");
        builder.append("客户端ip地址=" + clientIp);
        return builder.toString();
    }
}
