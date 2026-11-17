package week_02;
import java.util.Arrays; import javax.crypto.Cipher; import javax.crypto.spec.*;
public final class AESCTRCipher {
 private AESCTRCipher(){}
 public static String newKey(){return CipherUtil.b64(CipherUtil.randomBytes(32));} public static String newNonce(){return CipherUtil.b64(CipherUtil.randomBytes(12));}
 private static byte[] crypt(int mode,byte[] in,String kb,String nb)throws Exception{byte[] k=CipherUtil.fromB64(kb),n=CipherUtil.fromB64(nb);if(k.length!=32||n.length!=12)throw new IllegalArgumentException("Key cần 32 byte và Nonce cần 12 byte Base64.");byte[] counter=Arrays.copyOf(n,16);counter[15]=1;Cipher c=Cipher.getInstance("AES/CTR/NoPadding");c.init(mode,new SecretKeySpec(k,"AES"),new IvParameterSpec(counter));return c.doFinal(in);}
 public static String encrypt(String p,String k,String n)throws Exception{return CipherUtil.b64(crypt(Cipher.ENCRYPT_MODE,CipherUtil.utf8(p),k,n));} public static String decrypt(String x,String k,String n)throws Exception{return CipherUtil.text(crypt(Cipher.DECRYPT_MODE,CipherUtil.fromB64(x),k,n));}
 public static String toFile(String n,String x){return "NONCE="+n.trim()+"\n"+x.trim()+"\n";} public static String[] fromFile(String s){String[] p=s.trim().split("\\R",2);if(p.length<2||!p[0].startsWith("NONCE="))throw new IllegalArgumentException("Tệp cần dòng đầu NONCE=..., dòng sau là bản mã.");return new String[]{p[0].substring(6).trim(),p[1].trim()};}
}
