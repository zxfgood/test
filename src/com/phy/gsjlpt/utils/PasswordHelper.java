package com.phy.gsjlpt.utils;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**   
 * @ClassName:  PasswordHelper   
 * @Description:TODO(密码生成器)
 * @author: 张晓峰 
 * @date:   2018年1月30日 上午9:30:09     
 */  
public class PasswordHelper {
	//对应spring-shiro.xml文件中的凭证匹配器
	//获得随机数的盐
	 private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	 //加密方式
	 private HashedCredentialsMatcher hashedCredentialsMatcher;
	    public PasswordHelper(HashedCredentialsMatcher hashedCredentialsMatcher) {
	        this.hashedCredentialsMatcher = hashedCredentialsMatcher;
	    }

	    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
	        return hashedCredentialsMatcher;
	    }

	    public void setHashedCredentialsMatcher(HashedCredentialsMatcher hashedCredentialsMatcher) {
	        this.hashedCredentialsMatcher = hashedCredentialsMatcher;
	    }
	    //获得随机数的盐
	    public String getRandomSalt(){
	        return randomNumberGenerator.nextBytes().toHex();
	    }
	    //生成新的密码
	    public String encryptPassword(String password,String credentialsSalt){
	    	//获得加密的算法例如md5
	    	String hashAlgorithmName=hashedCredentialsMatcher.getHashAlgorithmName();
	    	//进行几次加密
	    	int hashIterations=hashedCredentialsMatcher.getHashIterations();
	    	//获得另外的盐，一般是数据库中的username+salt
	    	ByteSource byteSource=ByteSource.Util.bytes(credentialsSalt);
	    	//生成新密码
	    	SimpleHash simpleHash=new SimpleHash(hashAlgorithmName, password, byteSource, hashIterations);
	    	String newpassword=simpleHash.toHex();
			return newpassword;
	    } 
}
