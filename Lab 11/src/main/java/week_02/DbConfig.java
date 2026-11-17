package week_02;

import java.sql.*;

/** SQLite local database. File is created beside the project on first run. */
public final class DbConfig {
    private static final String URL="jdbc:sqlite:lab2.db";
    private DbConfig() {}
    public static Connection open() throws SQLException { Connection c=DriverManager.getConnection(URL); try(Statement s=c.createStatement()){s.execute("PRAGMA foreign_keys=ON");} return c; }
    public static void initialize() {
        String users="CREATE TABLE IF NOT EXISTS app_users(user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, salt BLOB NOT NULL, password_hash BLOB NOT NULL, created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP)";
        String hist="CREATE TABLE IF NOT EXISTS crypto_history(history_id INTEGER PRIMARY KEY AUTOINCREMENT, algorithm TEXT NOT NULL, operation TEXT NOT NULL, app_username TEXT, input_length INTEGER NOT NULL, output_length INTEGER NOT NULL, created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP)";
        try(Connection c=open();Statement s=c.createStatement()){s.execute(users);s.execute(hist);}catch(SQLException e){throw new IllegalStateException("Không khởi tạo được SQLite: "+e.getMessage(),e);}
    }
    public static String test() throws SQLException { try(Connection c=open();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT sqlite_version()")){return "SQLite kết nối thành công (phiên bản "+r.getString(1)+").";} }
}
