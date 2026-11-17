package week_02;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class CipherUtil {
    private static final SecureRandom RNG = new SecureRandom();
    private CipherUtil() {}
    public static byte[] utf8(String s) { return s.getBytes(StandardCharsets.UTF_8); }
    public static String text(byte[] b) { return new String(b, StandardCharsets.UTF_8); }
    public static String b64(byte[] b) { return Base64.getEncoder().encodeToString(b); }
    public static byte[] fromB64(String s) { try { return Base64.getDecoder().decode(s.trim()); } catch (Exception e) { throw new IllegalArgumentException("Chuỗi Base64 không hợp lệ."); } }
    public static byte[] randomBytes(int n) { byte[] b = new byte[n]; RNG.nextBytes(b); return b; }
    public static String hex(byte[] b) { return java.util.HexFormat.of().withUpperCase().formatHex(b); }
    public static byte[] fromHex(String s) { try { return java.util.HexFormat.of().parseHex(s.trim()); } catch (Exception e) { throw new IllegalArgumentException("Chuỗi HEX không hợp lệ."); } }
    public static void requireKey(String k) { if (k == null || k.isEmpty()) throw new IllegalArgumentException("Hãy nhập Key."); }
    public static byte[] concat(byte[]... chunks) { int n=0; for(byte[] c:chunks)n+=c.length; byte[] o=new byte[n]; int p=0; for(byte[] c:chunks){System.arraycopy(c,0,o,p,c.length);p+=c.length;} return o; }
    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bits) {
        try { PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bits); try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); } finally { spec.clearPassword(); } }
        catch(Exception e){throw new IllegalStateException("Không tạo được khóa PBKDF2",e);}
    }
}
