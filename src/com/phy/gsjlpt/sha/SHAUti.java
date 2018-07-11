package com.phy.gsjlpt.sha;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**   
 * @ClassName:  SHAUti  
 * @Description:TODO(SHA密码加密)
 * @author: 张晓峰 
 * @date:   2018年1月11日 下午9:44:15     
 */
public class SHAUti {
	public static String shaEncode(String instr) throws UnsupportedEncodingException{
		MessageDigest sha=null;
		try {
			sha=MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return "";
		}
		//修改编码格式
		byte[] byteArray=instr.getBytes("UTF-8");
		byte[] md5bBytes=sha.digest(byteArray);
		StringBuffer hexValue=new StringBuffer();
		for(int i=0;i<md5bBytes.length;i++){
			int val=((int)md5bBytes[i])&0xff;
			if(val<16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	public static void main(String[] args) {
		try {
			System.out.println(shaEncode("a"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

