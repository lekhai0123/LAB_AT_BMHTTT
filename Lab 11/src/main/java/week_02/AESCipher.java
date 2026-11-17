package week_02;
import java.util.Arrays; import javax.crypto.Cipher; import javax.crypto.spec.*;
public final class AESCipher {
 private static final int ITER=120_000; private AESCipher(){}
 public static String encrypt(String p,String password)throws Exception{CipherUtil.requireKey(password);byte[] salt=CipherUtil.randomBytes(16),nonce=CipherUtil.randomBytes(12),key=CipherUtil.pbkdf2(password.toCharArray(),salt,ITER,256);Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,nonce));return CipherUtil.b64(CipherUtil.concat(salt,nonce,c.doFinal(CipherUtil.utf8(p))));}
 public static String decrypt(String x,String password)throws Exception{CipherUtil.requireKey(password);byte[] a=CipherUtil.fromB64(x);if(a.length<44)throw new IllegalArgumentException("Bản mã AES quá ngắn hoặc bị hỏng.");byte[] salt=Arrays.copyOfRange(a,0,16),nonce=Arrays.copyOfRange(a,16,28),key=CipherUtil.pbkdf2(password.toCharArray(),salt,ITER,256);Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,nonce));return CipherUtil.text(c.doFinal(Arrays.copyOfRange(a,28,a.length)));}
}
