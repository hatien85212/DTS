package com.dts.adminportal.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesEncryption {	

	private String encryptionAlgorithm =  "AES";	
	private String encryptionKey;	
	
	public AesEncryption(String encryptionKey) {
		this.encryptionKey = encryptionKey;		
	}

	private Cipher getCipher(int cipherMode) throws Exception {
		SecretKeySpec keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
		Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
		cipher.init(cipherMode, keySpecification);
		return cipher;
	}
	
	public String encryptString(String plainText) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.encodeBase64String(encryptedBytes);
	}

	public String decryptString(String encrypted) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
		byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		return new String(plainBytes);
	}

	public byte[] encryptBinary(byte[] data) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);		
	}

	public byte[] decryptBinary(byte[] encryptedData) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);		
		return cipher.doFinal(encryptedData);
	}	
}