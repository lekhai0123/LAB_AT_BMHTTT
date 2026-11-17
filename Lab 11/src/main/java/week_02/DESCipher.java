package week_02;
import javax.crypto.Cipher; import javax.crypto.spec.*;
public final class DESCipher {
 private DESCipher(){}
 private static SecretKeySpec key(String k){CipherUtil.requireKey(k);byte[] b=CipherUtil.utf8(k);if(b.length!=8)throw new IllegalArgumentException("Khóa DES phải đúng 8 byte ASCII.");return new SecretKeySpec(b,"DES");}
 public static String encrypt(String p,String k)throws Exception{byte[] iv=CipherUtil.randomBytes(8);Cipher c=Cipher.getInstance("DES/CBC/PKCS5Padding");c.init(Cipher.ENCRYPT_MODE,key(k),new IvParameterSpec(iv));return CipherUtil.b64(CipherUtil.concat(iv,c.doFinal(CipherUtil.utf8(p))));}
 public static String decrypt(String x,String k)throws Exception{byte[] a=CipherUtil.fromB64(x);if(a.length<16)throw new IllegalArgumentException("Bản mã quá ngắn hoặc bị hỏng.");Cipher c=Cipher.getInstance("DES/CBC/PKCS5Padding");c.init(Cipher.DECRYPT_MODE,key(k),new IvParameterSpec(java.util.Arrays.copyOfRange(a,0,8)));return CipherUtil.text(c.doFinal(java.util.Arrays.copyOfRange(a,8,a.length)));}
}
