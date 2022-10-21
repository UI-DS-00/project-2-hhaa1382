package Pages;

import Elements.Music;
import Elements.MyLinkList;

import javax.swing.*;
import java.awt.*;

public class MusicListPart extends JFrame {
    MyLinkList<Music> allMusics;
    public MusicListPart(MyLinkList<Music> allMusics){
        this.allMusics=allMusics;

        this.setTitle("Music Player");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        DefaultListModel<String> values=new DefaultListModel<>();
        addMusicList(values);
        JList<String> list=new JList<>(values);

        JScrollPane panel=new JScrollPane(list);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(10,10,500,400);

        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    private void addMusicList(DefaultListModel<String> values){
        for(int i=0;i<allMusics.size();i++){
            Music m=allMusics.get(i);
            values.addElement(m.toString());
        }
    }
}
