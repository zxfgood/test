package com.phy.gsjlpt.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

public class AnyRolesFilter extends AuthorizationFilter{
	private String unauthorizedUrl= "/user/unauthorized2";
    private String loginUrl = "/user/login";
	/*
	 * @see org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 *判断用户的角色，相当于拦截器一样，当用户没登录时，想访问什么网页会跳转到什么网页，没角色权限会跳转到什么页面
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// TODO 自动生成的方法存根
		//获得当前用户
		Subject subject=getSubject(request, response);
		String[] roles=(String[]) mappedValue;
		//{//如果没有设置角色参数，默认成功
		if(roles==null){
			return true;
		}
		//当有角色时跳转
		for(int i=0;i<roles.length;i++){
			if(subject.hasRole(roles[i])){
				return true;
			}
		}
		//返回的false交给了onAccessDenied去处理
		return false;
	}
	 protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

	        Subject subject = getSubject(request, response);
	        // If the subject isn't identified, redirect to login URL
	        if (subject.getPrincipal() == null) {
	            //saveRequestAndRedirectToLogin(request, response);
	        	//如果当前用户没有登录，重定向到登录页面
	        	 WebUtils.issueRedirect(request, response, unauthorizedUrl);
	        } else {
	            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
	            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
	           // String unauthorizedUrl = getUnauthorizedUrl();
	            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
	            if (StringUtils.hasText(unauthorizedUrl)) {
	            	//如果登录了没有通过验证跳转到权限不够页面
	                WebUtils.issueRedirect(request, response, unauthorizedUrl);
	            } else {
	            	//其他错误到默认的401页面
	                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	            }
	        }
	        return false;
	    }
}
