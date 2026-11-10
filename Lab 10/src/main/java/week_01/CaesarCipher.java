package week_01;
public final class CaesarCipher {
 private CaesarCipher(){}
 public static String encrypt(String s,int k){return transform(s,k);}public static String decrypt(String s,int k){return transform(s,-k);}
 private static String transform(String s,int k){StringBuilder b=new StringBuilder();int shift=Math.floorMod(k,26);for(char c:s.toCharArray()){char base=c>='A'&&c<='Z'?'A':c>='a'&&c<='z'?'a':0;if(base==0)b.append(c);else b.append((char)(base+Math.floorMod(c-base+shift,26)));}return b.toString();}
}
