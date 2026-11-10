package week_01;
public final class RailFenceCipher {
 private RailFenceCipher(){}
 public static String encrypt(String s,int rails){return process(s,rails,true);}public static String decrypt(String s,int rails){return process(s,rails,false);}
 private static String process(String s,int r,boolean enc){if(r<2||r>1000)throw new IllegalArgumentException("Số hàng phải từ 2 đến 1000.");int[] a=s.codePoints().toArray();if(a.length<2||r>=a.length)return s;int[] pat=new int[a.length];int row=0,dir=1;for(int i=0;i<a.length;i++){pat[i]=row;if(row==0)dir=1;else if(row==r-1)dir=-1;row+=dir;}int[] out=new int[a.length];if(enc){int n=0;for(int rail=0;rail<r;rail++)for(int i=0;i<a.length;i++)if(pat[i]==rail)out[n++]=a[i];}else{int[] ix=new int[a.length];int n=0;for(int rail=0;rail<r;rail++)for(int i=0;i<a.length;i++)if(pat[i]==rail)ix[i]=n++;for(int i=0;i<a.length;i++)out[i]=a[ix[i]];}return new String(out,0,out.length);}
}
