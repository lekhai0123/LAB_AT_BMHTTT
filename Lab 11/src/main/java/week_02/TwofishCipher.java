package week_02;

import java.security.MessageDigest;
import java.util.Arrays;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public final class TwofishCipher {
    private TwofishCipher() {}
    public static String newIv(){return CipherUtil.hex(CipherUtil.randomBytes(16));}
    private static byte[] run(boolean enc,byte[] input,String key,String ivHex)throws Exception{
        CipherUtil.requireKey(key);byte[] iv=CipherUtil.fromHex(ivHex);if(iv.length!=16)throw new IllegalArgumentException("IV phải đúng 16 byte (32 ký tự HEX). Bấm Generate IV.");
        byte[] k=MessageDigest.getInstance("SHA-256").digest(CipherUtil.utf8(key));
        BufferedBlockCipher c=new PaddedBufferedBlockCipher(CBCBlockCipher.newInstance(new TwofishEngine()));c.init(enc,new ParametersWithIV(new KeyParameter(k),iv));byte[] out=new byte[c.getOutputSize(input.length)];int n=c.processBytes(input,0,input.length,out,0);n+=c.doFinal(out,n);return Arrays.copyOf(out,n);
    }
    public static String encrypt(String p,String k,String iv)throws Exception{return CipherUtil.b64(run(true,CipherUtil.utf8(p),k,iv));}
    public static String decrypt(String x,String k,String iv)throws Exception{return CipherUtil.text(run(false,CipherUtil.fromB64(x),k,iv));}
    public static String toFile(String iv,String x){return "IV="+iv.trim()+"\n"+x.trim()+"\n";}
    public static String[] fromFile(String s){String[] p=s.trim().split("\\R",2);if(p.length<2||!p[0].startsWith("IV="))throw new IllegalArgumentException("Tệp cần dòng đầu IV=..., dòng sau là bản mã.");return new String[]{p[0].substring(3).trim(),p[1].trim()};}
}
