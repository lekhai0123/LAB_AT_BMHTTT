package week_02;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public final class HistoryDao {
    private HistoryDao() {}
    public static void insert(String alg,String op,String user,int in,int out)throws SQLException{
        try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("INSERT INTO crypto_history(algorithm,operation,app_username,input_length,output_length) VALUES(?,?,?,?,?)")){
            p.setString(1,alg);p.setString(2,op);p.setString(3,user);p.setInt(4,in);p.setInt(5,out);p.executeUpdate();
        }
    }
    public static String tryInsert(String alg,String op,String user,int in,int out){try{insert(alg,op,user,in,out);return "(đã ghi lịch sử SQLite)";}catch(SQLException e){return "(lỗi ghi lịch sử: "+e.getMessage()+")";}}
    public static DefaultTableModel loadLatest(int top)throws SQLException{
        DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","Thời gian","Thuật toán","Thao tác","User","Độ dài vào","Độ dài ra"},0){public boolean isCellEditable(int r,int c){return false;}};
        try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("SELECT history_id,created_at,algorithm,operation,app_username,input_length,output_length FROM crypto_history ORDER BY history_id DESC LIMIT ?")){p.setInt(1,top);try(ResultSet r=p.executeQuery()){while(r.next())m.addRow(new Object[]{r.getLong(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getInt(6),r.getInt(7)});}}
        return m;
    }
}
