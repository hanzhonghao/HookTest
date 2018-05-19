package utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class SymEncrypt {

	public static Key getKey(byte[] arrBTmp, String alg){
		if(!(alg.equals("DES")||alg.equals("DESede")||alg.equals("AES"))){
			System.out.println("alg type not find: "+alg);
			return null;
		}
		byte[] arrB;
		if(alg.equals("DES")){
			arrB = new byte[8];
		}
		else if(alg.equals("DESede")){
			arrB = new byte[24];
		}
		else{
			arrB = new byte[16];
		}
		int i=0;
		int j=0;
		while(i < arrB.length){
			if(j>arrBTmp.length-1){
				j=0;
			}
			arrB[i] = arrBTmp[j];
			i++;
			j++;
		}
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, alg);
		return key;
	}

	public static byte[] encrypt(String s,String strKey,String alg){
		if(!(alg.equals("DES")||alg.equals("DESede")||alg.equals("AES"))){
			System.out.println("alg type not find: "+alg);
			return null;
		}
		byte[] r=null;
		try {
			Key key = getKey(strKey.getBytes(),alg);
			Cipher c;
			c = Cipher.getInstance(alg);
			c.init(Cipher.ENCRYPT_MODE, key);
			r = c.doFinal(s.getBytes());
			//System.out.println("加密后的二进串:" + FileDigest.byte2Str(r));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	public static String decrypt(byte[] code,String strKey,String alg){
		if(!(alg.equals("DES")||alg.equals("DESede")||alg.equals("AES"))){
			System.out.println("alg type not find: "+alg);
			return null;
		}
		String r=null;
		try {
			Key key = getKey(strKey.getBytes(),alg);
			Cipher c;
			c = Cipher.getInstance(alg);
			c.init(Cipher.DECRYPT_MODE,key);
			byte[] clearByte=c.doFinal(code);
			r=new String(clearByte);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.out.println("not padding");
			r=null;
		}
		//System.out.println("解密后的信息:"+r);
		return r;
	}

}
