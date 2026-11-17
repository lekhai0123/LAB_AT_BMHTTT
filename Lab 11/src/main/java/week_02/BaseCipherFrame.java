package week_02;

import java.awt.*;
import javax.swing.*;

/** Shared Swing interface for the four introductory cipher exercises. */
public class BaseCipherFrame extends JFrame {
    private final String algorithm, kind;
    protected final JTextArea plain=new JTextArea(4,58), cipher=new JTextArea(4,58);
    protected final JTextField key, iv=new JTextField(38);
    protected final JLabel status=new JLabel("Sẵn sàng.");
    public BaseCipherFrame(String title,String algorithm,String kind,String keyHint,String initialKey){
        super(title);this.algorithm=algorithm;this.kind=kind;key=new JTextField(initialKey,34);setDefaultCloseOperation(DISPOSE_ON_CLOSE);setLocationByPlatform(true);
        JPanel root=new JPanel();root.setBorder(BorderFactory.createEmptyBorder(18,20,18,20));root.setLayout(new BoxLayout(root,BoxLayout.Y_AXIS));
        JLabel heading=new JLabel(title.toUpperCase());heading.setFont(new Font("Segoe UI",Font.BOLD,20));root.add(heading);root.add(Box.createVerticalStrut(12));
        root.add(new JLabel("Plaintext:"));root.add(area(plain));root.add(Box.createVerticalStrut(8));
        JPanel kr=new JPanel(new FlowLayout(FlowLayout.LEFT,8,2));kr.add(new JLabel("Key:"));kr.add(key);kr.add(new JLabel(keyHint));root.add(kr);
        if(kind.equals("TWOFISH")){JPanel ir=new JPanel(new FlowLayout(FlowLayout.LEFT,8,2));ir.add(new JLabel("IV (HEX):"));ir.add(iv);JButton g=new JButton("Generate IV");g.addActionListener(e->{iv.setText(TwofishCipher.newIv());status.setText("Đã tạo IV ngẫu nhiên 16 byte.");});ir.add(g);root.add(ir);}
        root.add(new JLabel("Ciphertext:"));root.add(area(cipher));JPanel buttons=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton enc=new JButton("Encrypt"),dec=new JButton("Decrypt"),save=new JButton("Save File"),open=new JButton("Open File");
        enc.addActionListener(e->encrypt());dec.addActionListener(e->decrypt());save.addActionListener(e->save());open.addActionListener(e->open());buttons.add(enc);buttons.add(dec);buttons.add(save);buttons.add(open);root.add(buttons);root.add(status);
        setContentPane(root);pack();setMinimumSize(new Dimension(760,430));setLocationRelativeTo(null);
    }
    private JScrollPane area(JTextArea a){a.setLineWrap(true);a.setWrapStyleWord(true);a.setFont(new Font(Font.MONOSPACED,Font.PLAIN,13));return new JScrollPane(a);}
    private String encryptValue(String p)throws Exception{return switch(kind){case "DES"->DESCipher.encrypt(p,key.getText());case "3DES"->TripleDESCipher.encrypt(p,key.getText());case "RC4"->RC4Cipher.encrypt(p,key.getText());case "TWOFISH"->TwofishCipher.encrypt(p,key.getText(),iv.getText());default->throw new IllegalStateException();};}
    private String decryptValue(String x)throws Exception{return switch(kind){case "DES"->DESCipher.decrypt(x,key.getText());case "3DES"->TripleDESCipher.decrypt(x,key.getText());case "RC4"->RC4Cipher.decrypt(x,key.getText());case "TWOFISH"->TwofishCipher.decrypt(x,key.getText(),iv.getText());default->throw new IllegalStateException();};}
    private void encrypt(){String p=plain.getText();if(p.isEmpty()){JOptionPane.showMessageDialog(this,"Hãy nhập Plaintext trước khi mã hóa.");return;}try{cipher.setText(encryptValue(p));status.setText("Đã mã hóa "+algorithm+". "+HistoryDao.tryInsert(algorithm,"ENCRYPT",null,p.length(),cipher.getText().length()));}catch(Exception e){showError(e,"Mã hóa thất bại");}}
    private void decrypt(){if(cipher.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Hãy nhập Ciphertext trước khi giải mã.");return;}try{String p=decryptValue(cipher.getText());plain.setText(p);status.setText("Đã giải mã "+algorithm+". "+HistoryDao.tryInsert(algorithm,"DECRYPT",null,cipher.getText().length(),p.length()));}catch(Exception e){showError(e,"Giải mã thất bại: sai Key/IV hoặc bản mã bị sửa");}}
    private void showError(Exception e,String prefix){JOptionPane.showMessageDialog(this,prefix+": "+e.getMessage(),"Lỗi",JOptionPane.ERROR_MESSAGE);}
    private void save(){if(cipher.getText().isBlank()){JOptionPane.showMessageDialog(this,"Chưa có bản mã.");return;}String v=kind.equals("TWOFISH")?TwofishCipher.toFile(iv.getText(),cipher.getText()):cipher.getText();String path=FileTextIO.save(this,v,kind.toLowerCase()+"_ciphertext.txt");if(path!=null)status.setText("Đã lưu: "+path);}
    private void open(){String v=FileTextIO.open(this);if(v==null)return;try{if(kind.equals("TWOFISH")){String[] p=TwofishCipher.fromFile(v);iv.setText(p[0]);cipher.setText(p[1]);}else cipher.setText(v.trim());status.setText("Đã mở tệp.");}catch(Exception e){showError(e,"Tệp không hợp lệ");}}
}
