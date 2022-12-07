package org.cloud.blog.filter;

import com.google.gson.JsonObject;
import org.cloud.blog.common.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class GlobalRouterFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if(antPathMatcher.match("/ucenter/sysUser/login", path) ||
            antPathMatcher.match("/ucenter/sysUser/register", path) ||
                antPathMatcher.match("/article/index/**", path) ||
                    antPathMatcher.match("/comment/views/**", path)){
            return chain.filter(exchange);
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> tokens = headers.get("Oauth-Token");
        if(null == tokens || tokens.size() <= 0){
            return out(exchange.getResponse());
        }
        String token = tokens.get(0);
        if(!StringUtils.isEmpty(token)){
            boolean bool = JwtUtils.checkTokenSecurity(token);
            if(bool){
                String authId = JwtUtils.getUserId(token);
                ServerHttpRequest request = exchange.getRequest().mutate().header("authId", authId).build();
                return chain.filter(exchange.mutate().request(request).build());
            }
            return out(exchange.getResponse());
        }
        return out(exchange.getResponse());
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 70001);
        message.addProperty("msg", "您的权限不足");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
