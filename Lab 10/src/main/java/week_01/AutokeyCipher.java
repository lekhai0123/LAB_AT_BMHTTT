package week_01;
import java.util.Locale;
public final class AutokeyCipher {
 private AutokeyCipher(){}
 public static String encrypt(String s,String k){return process(s,k,true);}public static String decrypt(String s,String k){return process(s,k,false);}
 private static String process(String s,String key,boolean enc){String k=key.trim().toUpperCase(Locale.ROOT);if(!k.matches("[A-Z]+"))throw new IllegalArgumentException("Khóa Autokey chỉ gồm A-Z.");StringBuilder out=new StringBuilder(),plain=new StringBuilder();int pos=0;for(char c:s.toCharArray()){if(!((c>='A'&&c<='Z')||(c>='a'&&c<='z'))){out.append(c);continue;}char base=c>='A'&&c<='Z'?'A':'a';int shift=pos<k.length()?k.charAt(pos)-'A':plain.charAt(pos-k.length())-'A';char z=(char)(base+Math.floorMod(c-base+(enc?shift:-shift),26));out.append(z);plain.append(enc?c:z);pos++;}return out.toString();}
}
