package Pages;

import javax.swing.*;
import java.awt.*;

public class MusicListPart extends JFrame {
    public MusicListPart(){
        this.setTitle("Music Player");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        DefaultListModel<String> values=new DefaultListModel<>();
        JList<String> list=new JList<>(values);

        JScrollPane panel=new JScrollPane(list);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(10,10,500,400);

        this.getContentPane().add(panel);
        this.setVisible(true);
    }
}
