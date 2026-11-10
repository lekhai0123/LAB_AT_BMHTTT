package week_01;
public final class TranspositionCipher {
 private TranspositionCipher(){}
 private static int[] key(String s){String[] t=s.trim().split("[,\\s]+",-1);if(t.length<2)throw new IllegalArgumentException("Khóa cần là hoán vị, ví dụ 2,3,1.");int[] a=new int[t.length];boolean[] seen=new boolean[t.length];for(int i=0;i<a.length;i++){try{a[i]=Integer.parseInt(t[i].trim())-1;}catch(Exception e){throw new IllegalArgumentException("Khóa cần là hoán vị, ví dụ 2,3,1.");}if(a[i]<0||a[i]>=a.length||seen[a[i]])throw new IllegalArgumentException("Các số khóa phải là hoán vị 1..n.");seen[a[i]]=true;}return a;}
 public static String encrypt(String s,String k){int[] ord=key(k);StringBuilder b=new StringBuilder();for(int c:ord)for(int i=c;i<s.length();i+=ord.length)b.append(s.charAt(i));return b.toString();}
 public static String decrypt(String s,String k){int[] ord=key(k);int n=s.length(),cols=ord.length,rows=(n+cols-1)/cols;char[][] grid=new char[rows][cols];int[] lengths=new int[cols];for(int c=0;c<cols;c++)lengths[c]=Math.max(0,Math.min(rows,n-c*rows));int pos=0;for(int c:ord)for(int r=0;r<lengths[c];r++)grid[r][c]=s.charAt(pos++);StringBuilder b=new StringBuilder();for(int r=0;r<rows;r++)for(int c=0;c<cols;c++)if(grid[r][c]!=0)b.append(grid[r][c]);return b.toString();}
}
