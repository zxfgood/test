package com.phy.gsjlpt.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.UserService;



public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserService userservice;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		// TODO 自动生成的方法存根
		//获得当前用户
		User user=(User) principal.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//添加用户角色
		authorizationInfo.setRoles(userservice.getRole(user.getUsername()));
		//添加用户权限
		authorizationInfo.setStringPermissions(userservice.getPermission(user.getUsername()));
		return authorizationInfo;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO 自动生成的方法存根
		String username=(String) token.getPrincipal();
		User user=userservice.findByUsername(username);
		if(user==null){
			 throw new UnknownAccountException();//没找到帐号
		}
		if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 存放用户
                user.getPassword(), // 得到数据库密码和token的密码进行比对
                ByteSource.Util.bytes(user.getCredentialsSalt()), // salt=username+salt
                this.getName() // realm name
        );
		return  authenticationInfo;
	}
	/*
	 * （非 Javadoc）
	 * @see org.apache.shiro.realm.AuthorizingRealm#clearCachedAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 *在清除缓存
	 */
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
