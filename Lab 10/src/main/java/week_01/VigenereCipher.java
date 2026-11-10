package week_01;
import java.util.Locale;
public final class VigenereCipher {
 private VigenereCipher(){}
 public static String encrypt(String s,String k){return process(s,k,true);}public static String decrypt(String s,String k){return process(s,k,false);}
 private static String process(String s,String key,boolean enc){String k=key.trim().toUpperCase(Locale.ROOT);if(!k.matches("[A-Z]+"))throw new IllegalArgumentException("Khóa Vigenère chỉ gồm A-Z.");StringBuilder b=new StringBuilder();int j=0;for(char c:s.toCharArray()){char base=c>='A'&&c<='Z'?'A':c>='a'&&c<='z'?'a':0;if(base==0)b.append(c);else{int shift=k.charAt(j++%k.length())-'A';b.append((char)(base+Math.floorMod(c-base+(enc?shift:-shift),26)));}}return b.toString();}
}
