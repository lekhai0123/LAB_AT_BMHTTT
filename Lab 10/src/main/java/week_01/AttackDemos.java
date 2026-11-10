package week_01;
import java.util.Locale;
public final class AttackDemos {
 private static final double[] EN={8.2,1.5,2.8,4.3,12.7,2.2,2.0,6.1,7.0,.15,.77,4.0,2.4,6.7,7.5,1.9,.095,6.0,6.3,9.1,2.8,.98,2.4,.15,2.0,.074};
 private AttackDemos(){}
 public static String brute(String cipher){int best=0;double bestScore=-Double.MAX_VALUE;for(int k=0;k<26;k++){double score=score(CaesarCipher.decrypt(cipher,k));if(score>bestScore){bestScore=score;best=k;}}StringBuilder b=new StringBuilder("k  | Bản rõ thử\n");for(int k=0;k<26;k++)b.append(String.format(Locale.ROOT,"%2d | %s%s%n",k,CaesarCipher.decrypt(cipher,k),k==best?"  <== có điểm cao nhất":""));return b.append("Khóa có điểm tần suất cao nhất: ").append(best).append("; bản rõ: ").append(CaesarCipher.decrypt(cipher,best)).toString();}
 private static double score(String s){double sum=0;int n=0;for(char c:s.toUpperCase(Locale.ROOT).toCharArray())if(c>='A'&&c<='Z'){sum+=Math.log(EN[c-'A']);n++;}return n==0?-Double.MAX_VALUE:sum/n;}
 public static String frequency(String s){int[] n=new int[26];int total=0;for(char c:s.toUpperCase(Locale.ROOT).toCharArray())if(c>='A'&&c<='Z'){n[c-'A']++;total++;}if(total==0)return "Bản mã không có chữ cái A-Z.";StringBuilder b=new StringBuilder("Chữ | Số lần | Tần suất\n");for(int i=0;i<26;i++)if(n[i]>0)b.append(String.format(Locale.ROOT,"%c   | %d | %.1f%%%n",'A'+i,n[i],100.0*n[i]/total));return b.append("Tổng số chữ: ").append(total).toString();}
 public static String known(String p,String c){p=p.trim().toUpperCase(Locale.ROOT);c=c.trim().toUpperCase(Locale.ROOT);if(p.length()!=c.length()||!p.matches("[A-Z]+")||!c.matches("[A-Z]+"))throw new IllegalArgumentException("Bản rõ và bản mã phải cùng độ dài, chỉ gồm A-Z.");StringBuilder b=new StringBuilder();for(int i=0;i<p.length();i++)b.append((char)('A'+Math.floorMod(c.charAt(i)-p.charAt(i),26)));return b.toString();}
}
