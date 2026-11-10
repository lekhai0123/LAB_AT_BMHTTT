package week_01;
import java.sql.*;import javax.swing.table.DefaultTableModel;
public final class HistoryDao {
 private HistoryDao(){}
 public static void insert(String a,String op,String input,String result)throws SQLException{try(Connection c=DbConfig.connect();PreparedStatement p=c.prepareStatement("INSERT INTO history(algorithm,operation,input_text,result_text) VALUES(?,?,?,?)")){p.setString(1,a);p.setString(2,op);p.setString(3,input);p.setString(4,result);p.executeUpdate();}}
 public static DefaultTableModel load(int limit)throws SQLException{DefaultTableModel m=new DefaultTableModel(new String[]{"ID","Thời gian","Thuật toán","Thao tác","Dữ liệu vào","Kết quả"},0){public boolean isCellEditable(int r,int c){return false;}};try(Connection c=DbConfig.connect();PreparedStatement p=c.prepareStatement("SELECT id,created_at,algorithm,operation,input_text,result_text FROM history ORDER BY id DESC LIMIT ?")){p.setInt(1,limit);try(ResultSet r=p.executeQuery()){while(r.next())m.addRow(new Object[]{r.getLong(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6)});}}return m;}
}
