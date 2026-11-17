package week_02;
import java.awt.*;import javax.swing.*;
public class frm_LichSu extends JFrame {
 private final JTable table=new JTable();private final JLabel status=new JLabel("Đang tải...");
 public frm_LichSu(){super("Lịch sử thao tác SQLite");setDefaultCloseOperation(DISPOSE_ON_CLOSE);JPanel p=new JPanel(new BorderLayout(8,8));p.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));JLabel h=new JLabel("LỊCH SỬ THAO TÁC - CRYPTO_HISTORY");h.setFont(new Font("Segoe UI",Font.BOLD,18));p.add(h,BorderLayout.NORTH);p.add(new JScrollPane(table),BorderLayout.CENTER);JPanel bottom=new JPanel(new BorderLayout());JButton r=new JButton("Tải lại");r.addActionListener(e->load());bottom.add(r,BorderLayout.WEST);bottom.add(status,BorderLayout.CENTER);p.add(bottom,BorderLayout.SOUTH);setContentPane(p);setSize(850,420);setLocationRelativeTo(null);load();}
 private void load(){try{table.setModel(HistoryDao.loadLatest(100));status.setText("Đã tải "+table.getRowCount()+" dòng từ SQLite.");}catch(Exception e){status.setText("Không đọc được lịch sử: "+e.getMessage());}}
}
