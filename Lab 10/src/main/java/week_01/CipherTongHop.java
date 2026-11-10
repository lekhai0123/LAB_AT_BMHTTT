package week_01;
public final class CipherTongHop {
 private CipherTongHop(){}
 public static String[] names(){return new String[]{"Caesar","Vigenere","Rail Fence","Playfair","Transposition","Autokey"};}
 public static String sampleKey(String a){return switch(a){case "Caesar","Rail Fence"->"3";case "Vigenere","Autokey"->"KEY";case "Playfair"->"KEYWORD";case "Transposition"->"2,3,1";default->"";};}
 public static String encrypt(String a,String s,String k){return apply(a,s,k,true);}public static String decrypt(String a,String s,String k){return apply(a,s,k,false);}
 private static String apply(String a,String s,String k,boolean e){return switch(a){case "Caesar"->e?CaesarCipher.encrypt(s,Integer.parseInt(k.trim())):CaesarCipher.decrypt(s,Integer.parseInt(k.trim()));case "Vigenere"->e?VigenereCipher.encrypt(s,k):VigenereCipher.decrypt(s,k);case "Rail Fence"->e?RailFenceCipher.encrypt(s,Integer.parseInt(k.trim())):RailFenceCipher.decrypt(s,Integer.parseInt(k.trim()));case "Playfair"->e?PlayfairCipher.encrypt(s,k):PlayfairCipher.decrypt(s,k);case "Transposition"->e?TranspositionCipher.encrypt(s,k):TranspositionCipher.decrypt(s,k);case "Autokey"->e?AutokeyCipher.encrypt(s,k):AutokeyCipher.decrypt(s,k);default->throw new IllegalArgumentException("Thu?t to?n kh?ng h?p l?.");};}
}
