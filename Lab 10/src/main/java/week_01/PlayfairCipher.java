package week_01;
import java.util.*;
public final class PlayfairCipher {
 private PlayfairCipher(){}
 private static char[][] square(String key){String x=(key+"ABCDEFGHIJKLMNOPQRSTUVWXYZ").toUpperCase(Locale.ROOT).replace('J','I');StringBuilder u=new StringBuilder();for(char c:x.toCharArray())if(c>='A'&&c<='Z'&&u.indexOf(""+c)<0)u.append(c);char[][] a=new char[5][5];for(int i=0;i<25;i++)a[i/5][i%5]=u.charAt(i);return a;}
 public static String encrypt(String text,String key){return process(text,key,true);}public static String decrypt(String text,String key){return process(text,key,false);}
 private static String process(String text,String key,boolean enc){char[][] a=square(key);String s=text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]","").replace('J','I');if(enc){StringBuilder p=new StringBuilder();for(int i=0;i<s.length();){char x=s.charAt(i++),y=i<s.length()?s.charAt(i):'X';if(x==y){p.append(x).append('X');}else{p.append(x).append(y);i++;}}if(p.length()%2!=0)p.append('X');s=p.toString();}if(s.length()%2!=0)throw new IllegalArgumentException("B?n m? Playfair ph?i c? s? k? t? ch?n.");StringBuilder out=new StringBuilder();for(int i=0;i<s.length();i+=2){int[] p=find(a,s.charAt(i)),q=find(a,s.charAt(i+1));if(p[0]==q[0]){out.append(a[p[0]][(p[1]+(enc?1:4))%5]).append(a[q[0]][(q[1]+(enc?1:4))%5]);}else if(p[1]==q[1]){out.append(a[(p[0]+(enc?1:4))%5][p[1]]).append(a[(q[0]+(enc?1:4))%5][q[1]]);}else{out.append(a[p[0]][q[1]]).append(a[q[0]][p[1]]);}}return out.toString();}
 private static int[] find(char[][]a,char c){for(int r=0;r<5;r++)for(int col=0;col<5;col++)if(a[r][col]==c)return new int[]{r,col};throw new IllegalArgumentException("K? t? kh?ng h?p l?.");}
}
