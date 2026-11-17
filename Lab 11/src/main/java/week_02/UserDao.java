package week_02;

import java.security.MessageDigest;
import java.sql.*;

public final class UserDao {
    private static final int ITERATIONS=120_000;
    private UserDao() {}
    public static void register(String username,char[] password)throws SQLException{
        if(username==null||!username.matches("[A-Za-z0-9_.]{3,40}"))throw new IllegalArgumentException("Username 3-40 ký tự, chỉ gồm chữ, số, dấu _ hoặc dấu chấm.");
        if(password.length<8)throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự.");
        byte[] salt=CipherUtil.randomBytes(16), hash=CipherUtil.pbkdf2(password,salt,ITERATIONS,256);
        try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("INSERT INTO app_users(username,salt,password_hash) VALUES(?,?,?)")){p.setString(1,username);p.setBytes(2,salt);p.setBytes(3,hash);p.executeUpdate();}
        catch(SQLException e){if(e.getMessage()!=null&&e.getMessage().toLowerCase().contains("unique"))throw new IllegalArgumentException("Username đã tồn tại, hãy chọn tên khác.");throw e;}
    }
    public static boolean login(String username,char[] password)throws SQLException{
        try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("SELECT salt,password_hash FROM app_users WHERE username=?")){p.setString(1,username);try(ResultSet r=p.executeQuery()){if(!r.next())return false;return MessageDigest.isEqual(r.getBytes(2),CipherUtil.pbkdf2(password,r.getBytes(1),ITERATIONS,256));}}
    }
    public static String newTicket(){return CipherUtil.hex(CipherUtil.randomBytes(16));}
}
