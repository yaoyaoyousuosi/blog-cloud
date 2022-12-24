package org.cloud.blog.admin.config;

import jdk.nashorn.internal.ir.CallNode;
import org.cloud.blog.admin.domain.Permission;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.service.PermissionService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 根据请求过滤出需要的角色
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource private PermissionService permissionService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    // /admin/role/delete/1
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String uri = ((FilterInvocation) o).getRequestUrl();
        if("/" .equals(uri) || "/favicon.ico".equals(uri) ||
                "/login.html".equals(uri)) {
            return null; // 不需要任何角色
        }
        if(antPathMatcher.match("/css/**", uri) || antPathMatcher.match("/img/**", uri)
                || antPathMatcher.match("/js/**", uri) || antPathMatcher.match("/plugins/**", uri)
                || antPathMatcher.match("/pages/img/**", uri)){
            return null;
        }
        System.out.println("访问的uri:" + uri);
        List<Permission> permissions = permissionService.permissionWithRole();
        for (Permission permission : permissions){
            if(antPathMatcher.match(permission.getPath(), uri)){
                if(permission.getDeleted()){
                    return SecurityConfig.createList("ROLE_Deleted");
                }else {
                    List<Role> roles = permission.getRoles();
                    int size = roles.size();
                    String[] needRoles = new String[size];
                    for (int i = 0; i < size; i++) {
                        needRoles[i] = roles.get(i).getName();
                    }
                    return SecurityConfig.createList(needRoles);
                }
            }
        }
        // 没有匹配上路径登录即可访问
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
