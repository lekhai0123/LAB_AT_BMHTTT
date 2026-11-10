package week_01;
import java.sql.*;
import java.nio.file.*;
public final class DbConfig {
 private static String path="lab1_history.db";
 private DbConfig(){}
 public static synchronized void setPath(String p){if(p==null||p.isBlank())throw new IllegalArgumentException("Chọn đường dẫn file SQLite.");path=p;}
 public static synchronized String getPath(){return path;}
 public static synchronized Connection connect() throws SQLException {
  try { Path f=Path.of(path).toAbsolutePath(); if(f.getParent()!=null)Files.createDirectories(f.getParent()); }
  catch(Exception e){throw new SQLException("Không tạo được file database: "+e.getMessage(),e);}
  Connection c=DriverManager.getConnection("jdbc:sqlite:"+Path.of(path).toAbsolutePath());
  try(Statement s=c.createStatement()){s.execute("PRAGMA foreign_keys = ON");s.execute("CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY AUTOINCREMENT, created_at TEXT NOT NULL DEFAULT (datetime('now','localtime')), algorithm TEXT NOT NULL, operation TEXT NOT NULL CHECK(operation IN ('ENCRYPT','DECRYPT')), input_text TEXT NOT NULL, result_text TEXT NOT NULL)");}
  return c;
 }
 public static String test() throws SQLException {try(Connection c=connect();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT sqlite_version()")){r.next();return "SQLite conectado; versión "+r.getString(1)+"; file "+Path.of(path).toAbsolutePath();}}
}
