package week_02;
public final class RC4Cipher {
 private RC4Cipher(){}
 public static byte[] apply(byte[] data,byte[] key){if(key.length<1||key.length>256)throw new IllegalArgumentException("Khóa RC4 phải từ 1 đến 256 byte.");int[] s=new int[256];for(int i=0;i<256;i++)s[i]=i;int j=0;for(int i=0;i<256;i++){j=(j+s[i]+(key[i%key.length]&255))&255;int t=s[i];s[i]=s[j];s[j]=t;}byte[] out=new byte[data.length];int i=0;j=0;for(int k=0;k<data.length;k++){i=(i+1)&255;j=(j+s[i])&255;int t=s[i];s[i]=s[j];s[j]=t;out[k]=(byte)(data[k]^s[(s[i]+s[j])&255]);}return out;}
 public static String encrypt(String p,String k){CipherUtil.requireKey(k);return CipherUtil.hex(apply(CipherUtil.utf8(p),CipherUtil.utf8(k)));}
 public static String decrypt(String x,String k){CipherUtil.requireKey(k);return CipherUtil.text(apply(CipherUtil.fromHex(x),CipherUtil.utf8(k)));}
}
