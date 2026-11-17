package week_02;

import java.awt.*;import javax.swing.*;
public class frm_KetNoiSQL extends JFrame {
 private final JLabel status=new JLabel("SQLite được tạo tự động tại lab2.db trong thư mục chạy.");
 public frm_KetNoiSQL(){super("Kết nối SQLite");setDefaultCloseOperation(DISPOSE_ON_CLOSE);JPanel p=new JPanel();p.setBorder(BorderFactory.createEmptyBorder(22,24,22,24));p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));JLabel h=new JLabel("CƠ SỞ DỮ LIỆU SQLITE LOCAL");h.setFont(new Font("Segoe UI",Font.BOLD,18));p.add(h);p.add(Box.createVerticalStrut(12));p.add(new JLabel("Không cần cài hoặc cấu hình máy chủ cơ sở dữ liệu."));p.add(new JLabel("Tệp dữ liệu: lab2.db (tự tạo khi chạy chương trình)"));JButton b=new JButton("Test Connection");b.addActionListener(e->{try{DbConfig.initialize();status.setText(DbConfig.test());}catch(Exception ex){status.setText("Kết nối lỗi: "+ex.getMessage());}});p.add(b);JButton hist=new JButton("Xem lịch sử");hist.addActionListener(e->new frm_LichSu().setVisible(true));p.add(hist);p.add(status);setContentPane(p);pack();setLocationRelativeTo(null);}
}
