package net.lising.lib.encrypt;

import org.apache.commons.codec.binary.Base64;

public class Base64Encrypt {

	/**
	 * 加密
	 * 
	 * @return
	 */
	public static String Base64EncryptPwd(String password) {
		return new String(Base64.encodeBase64(password.getBytes(), true));
	}

	/**
	 * 解密
	 * 
	 * @return
	 */
	public static String DecryptBase64Pwd(String password) {
		return new String(Base64.decodeBase64(password.getBytes()));
	}

}
