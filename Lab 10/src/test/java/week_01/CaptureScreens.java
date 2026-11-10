package week_01;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.sql.*;

/** Creates real Swing window captures under Xvfb for the report evidence. */
public final class CaptureScreens {
 private static Path out;
 private CaptureScreens() {}
 public static void main(String[] args) throws Exception {
  out=Path.of(args.length==0?"report/evidence":args[0]);Files.createDirectories(out);
  String[][] samples={{"HELLO","3"},{"HELLO","KEY"},{"HELLO WORLD","3"},{"HELLOWORLD","KEYWORD"},{"BAOMAT","2,3,1"},{"HELLO","KEY"}};
  String[] forms={"frm_Caesar","frm_Vigenere","frm_RailFence","frm_Playfair","frm_Transposition","frm_Autokey"};
  for(int i=0;i<forms.length;i++)captureCipher(forms[i],samples[i][0],samples[i][1],i+1);
  captureCombined();captureAttack();captureSqlResults();
 }
 private static void captureCipher(String cls,String plain,String key,int n)throws Exception{
  JFrame f=(JFrame)Class.forName("week_01."+cls).getConstructor().newInstance();
  JTextArea p=field(f,"plain",JTextArea.class);JTextField k=field(f,"key",JTextField.class);p.setText(plain);k.setText(key);
  button(f,"Encrypt").doClick();save(f,n);
 }
 private static void captureCombined()throws Exception{
  frm_TongHop f=new frm_TongHop();field(f,"algo",JComboBox.class).setSelectedItem("Playfair");field(f,"plain",JTextArea.class).setText("HELLOWORLD");field(f,"key",JTextField.class).setText("KEYWORD");button(f,"Encrypt").doClick();save(f,7);
 }
 private static void captureAttack()throws Exception{
  frm_TanCong f=new frm_TanCong();field(f,"cipher",JTextArea.class).setText("KHOOR");button(f,"Brute Force Caesar").doClick();save(f,8);
 }
 private static void captureSqlResults()throws Exception{
  Path db=Files.createTempFile("lab1-report-", ".db");DbConfig.setPath(db.toString());
  String[][] rows={{"Caesar","HELLO","KHOOR"},{"Vigenere","HELLO","RIJVS"},{"Rail Fence","HELLO WORLD","HOREL OLLWD"},{"Playfair","HELLOWORLD","GYIZSCOKCFBU"},{"Transposition","BAOMAT","AAOTBM"},{"Autokey","HELLO","RIJSS"}};
  for(String[] r:rows)HistoryDao.insert(r[0],"ENCRYPT",r[1],r[2]);
  StringBuilder text=new StringBuilder("SQLite - 03_Kiem_Tra_Du_Lieu.sql\n\nSELECT id, created_at, algorithm, operation, input_text, result_text\nFROM history ORDER BY id DESC LIMIT 20;\n\n");
  try(Connection c=DbConfig.connect();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT id,created_at,algorithm,operation,input_text,result_text FROM history ORDER BY id")){
   text.append("id | created_at | algorithm | operation | input_text | result_text\n");while(r.next())text.append(r.getLong(1)).append(" | ").append(r.getString(2)).append(" | ").append(r.getString(3)).append(" | ").append(r.getString(4)).append(" | ").append(r.getString(5)).append(" | ").append(r.getString(6)).append('\n');
  }
  try(Connection c=DbConfig.connect();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM history")){r.next();text.append("\nrecord_count = ").append(r.getInt(1));}
  JFrame f=new JFrame("SQLite - kết quả truy vấn");JTextArea area=new JTextArea(text.toString());area.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));area.setEditable(false);f.add(new JScrollPane(area));f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);save(f,9);Files.deleteIfExists(db);
 }
 private static <T>T field(Object o,String name,Class<T> type)throws Exception{Class<?> c=o.getClass();while(c!=null){try{Field f=c.getDeclaredField(name);f.setAccessible(true);return type.cast(f.get(o));}catch(NoSuchFieldException e){c=c.getSuperclass();}}throw new NoSuchFieldException(name);}
 private static JButton button(Container root,String label){for(Component c:root.getComponents()){if(c instanceof JButton b&&label.equals(b.getText()))return b;if(c instanceof Container sub){JButton found=buttonOrNull(sub,label);if(found!=null)return found;}}throw new IllegalArgumentException("Button not found: "+label);}
 private static JButton buttonOrNull(Container root,String label){for(Component c:root.getComponents()){if(c instanceof JButton b&&label.equals(b.getText()))return b;if(c instanceof Container sub){JButton b=buttonOrNull(sub,label);if(b!=null)return b;}}return null;}
 private static void save(JFrame f,int n)throws Exception{
  SwingUtilities.invokeAndWait(()->{f.setSize(1000,700);f.setLocation(20,20);f.setVisible(true);});
  Robot robot=new Robot();robot.delay(700);Rectangle bounds=f.getBounds();BufferedImage image=robot.createScreenCapture(bounds);ImageIO.write(image,"png",out.resolve(String.format("evidence_%02d.png",n)).toFile());
  SwingUtilities.invokeAndWait(f::dispose);
 }
}
