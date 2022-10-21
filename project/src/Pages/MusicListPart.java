package Pages;

import Elements.Music;
import Elements.MyLinkList;
import Elements.PlayList;

import javax.swing.*;

public class MusicListPart extends JFrame {
    private MyLinkList<Music> musics;
    private final MyLinkList<PlayList> playLists=new MyLinkList<>();

    private final JComboBox<String> playListsName=new JComboBox<>();

    private final DefaultListModel<String> values=new DefaultListModel<>();
    private JList<String> list;
    private final JScrollPane panel;

    public MusicListPart(MyLinkList<Music> allMusics){
        musics=allMusics;
        playLists.addLast(new PlayList(allMusics,"All musics"));
        playListsName.addItem("All musics");

        PlayList pl=new PlayList("fav1");
        pl.addMusic(allMusics.get(1));
        playLists.addLast(pl);
        playListsName.addItem("fav1");

        PlayList pl1=new PlayList("fav2");
        pl1.addMusic(allMusics.get(0));
        playLists.addLast(pl1);
        playListsName.addItem("fav2");

        this.setTitle("Music Player");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        playListsName.setBounds(530,170,150,30);

        JButton btnSwitch=new JButton("Switch");
        btnSwitch.setBounds(530,210,150,30);
        btnSwitch.addActionListener(e->{
            int index=playListsName.getSelectedIndex();
            if(index!=-1){
                values.removeAllElements();
                addMusicList(values,playLists.get(index).getMusics());
                setMusicList(playLists.get(index));
            }
        });

        JButton btnAddPlayList=new JButton("Add Play List");
        btnAddPlayList.setBounds(530,10,150,30);
        btnAddPlayList.addActionListener(e->{
            String name=JOptionPane.showInputDialog("Enter name for play list :");
            if(name!=null){
                if(checkPlayListName(name)){
                    playListsName.addItem(name);
                    playLists.addLast(new PlayList(name));
                    JOptionPane.showMessageDialog(null,"Play list add");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Play list name is duplicated");
                }
            }
        });

        JButton btnRemovePlayList=new JButton("Remove Play List");
        btnRemovePlayList.setBounds(530,50,150,30);
        btnRemovePlayList.addActionListener(e->{
            int index=playListsName.getSelectedIndex();
            if(index!=-1){
                playLists.remove(index);
                playListsName.removeItemAt(index);
                values.removeAllElements();
            }
        });

        JButton btnMerge=new JButton("Merge");
        btnMerge.setBounds(530,110,150,30);
        btnMerge.addActionListener(e->{
            String[] valuesName=new String[playLists.size()];
            for(int i=0;i<playLists.size();i++){
                valuesName[i]=playLists.get(i).getName();
            }

            String ans=(String) JOptionPane.showInputDialog(null,"Choose a play list","Play lists",
                    JOptionPane.QUESTION_MESSAGE,null,valuesName,null);

            if(ans!=null){
                int firstIndex=playListsName.getSelectedIndex();
                int secondIndex=getIndex(ans,valuesName);

                MyLinkList<Music> secondList=playLists.get(secondIndex).getMusics();
                MyLinkList<Music> firstList=playLists.get(firstIndex).getMusics();
                MyLinkList<Music> newList=new MyLinkList<>();

                for(int i=0;i<firstList.size();i++){
                    newList.addLast(firstList.get(i));
                }

                for(int i=0;i<secondList.size();i++){
                    if(!newList.contain(secondList.get(i))) {
                        newList.addLast(secondList.get(i));
                    }
                }

                String name=playLists.get(firstIndex).getName()+" & "+playLists.get(secondIndex).getName();
                PlayList p=new PlayList(newList,name);
                playLists.addLast(p);
                playListsName.addItem(name);
                values.removeAllElements();
                addMusicList(values,newList);

                JOptionPane.showMessageDialog(null,"Play lists merged");
            }
        });

        JButton btnAddMusic=new JButton("Add Music");
        btnAddMusic.setBounds(530,270,150,30);
        btnAddMusic.addActionListener(e->{
            String[] valuesName=new String[playLists.size()];
            for(int i=0;i<playLists.size();i++){
                valuesName[i]=playLists.get(i).getName();
            }

            String ans=(String) JOptionPane.showInputDialog(null,"Choose a play list","Play lists",
                    JOptionPane.QUESTION_MESSAGE,null,valuesName,null);

            if(ans!=null){
                PlayList temp=playLists.get(getIndex(ans,valuesName));
                temp.addMusic(musics.get(list.getSelectedIndex()));
            }
        });

        JButton btnRemoveMusic=new JButton("Remove music");
        btnRemoveMusic.setBounds(530,310,150,30);
        btnRemoveMusic.addActionListener(e->{
            int index=playListsName.getSelectedIndex();
            if(index!=-1){
                PlayList p=playLists.get(index);
                p.removeMusic(list.getSelectedIndex());
                values.removeElementAt(list.getSelectedIndex());
            }
        });

        addMusicList(values,allMusics);
        list=new JList<>(values);

        panel=new JScrollPane(list);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(10,10,500,400);

        this.add(btnAddPlayList);
        this.add(btnRemovePlayList);
        this.add(btnMerge);
        this.add(playListsName);
        this.add(btnSwitch);
        this.add(btnAddMusic);
        this.add(btnRemoveMusic);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    private void setMusicList(PlayList list){
        musics=list.getMusics();
    }

    private void addMusicList(DefaultListModel<String> values,MyLinkList<Music> musics){
        for(int i=0;i<musics.size();i++){
            Music m=musics.get(i);
            values.addElement(m.toString());
        }
    }

    private boolean checkPlayListName(String name){
        for(int i=0;i<playLists.size();i++){
            if(playLists.get(i).getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    private int getIndex(String answer,String[] values){
        for(int i=0;i<values.length;i++){
            if(values[i].equals(answer)){
                return i;
            }
        }
        return -1;
    }
}
