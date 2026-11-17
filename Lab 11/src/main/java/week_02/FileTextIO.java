package week_02;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FileTextIO {
    private FileTextIO() {}
    public static String open(JFrame parent) {
        JFileChooser c=new JFileChooser(); c.setFileFilter(new FileNameExtensionFilter("Text file (*.txt)","txt"));
        if(c.showOpenDialog(parent)!=JFileChooser.APPROVE_OPTION)return null;
        try{return Files.readString(c.getSelectedFile().toPath(),StandardCharsets.UTF_8);}catch(Exception e){JOptionPane.showMessageDialog(parent,"Không đọc được tệp: "+e.getMessage());return null;}
    }
    public static String save(JFrame parent,String value,String name) {
        JFileChooser c=new JFileChooser(); c.setSelectedFile(new File(name)); if(c.showSaveDialog(parent)!=JFileChooser.APPROVE_OPTION)return null;
        File f=c.getSelectedFile(); if(!f.getName().toLowerCase().endsWith(".txt"))f=new File(f.getParentFile(),f.getName()+".txt");
        try{Files.writeString(f.toPath(),value,StandardCharsets.UTF_8);return f.getAbsolutePath();}catch(Exception e){JOptionPane.showMessageDialog(parent,"Không lưu được tệp: "+e.getMessage());return null;}
    }
}
