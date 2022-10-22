package Pages;

import Elements.Music;
import Elements.MyLinkList;
import Elements.PlayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
//            int index=playListsName.getSelectedIndex();
//            if(index!=-1){
//                playLists.remove(index);
//                playListsName.removeItemAt(index);
//                values.removeAllElements();
//            }
            setFilter();
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

    private void setFilter(){
        JFrame filterFrame=new JFrame("Filter");
        filterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        filterFrame.setSize(300,400);
        filterFrame.setResizable(false);
        filterFrame.setLocationRelativeTo(null);
        filterFrame.setLayout(null);

        JTextField txtGenre=new JTextField();
        txtGenre.setEditable(false);
        txtGenre.setBounds(100,40,100,20);

        JCheckBox checkGenre=new JCheckBox("Genre");
        checkGenre.setSelected(false);
        checkGenre.setBounds(100,10,100,20);
        checkGenre.addActionListener(e->{
            txtGenre.setEditable(checkGenre.isSelected());
        });

        JTextField txtArtistName=new JTextField();
        txtArtistName.setEditable(false);
        txtArtistName.setBounds(100,110,100,20);

        JCheckBox checkArtistName=new JCheckBox("Artist Name");
        checkArtistName.setSelected(false);
        checkArtistName.setBounds(100,80,100,20);
        checkArtistName.addActionListener(e->{
            txtArtistName.setEditable(checkArtistName.isSelected());
        });

        JTextField txtData=new JTextField();
        txtData.setEditable(false);
        txtData.setBounds(100,180,100,20);

        JCheckBox checkDate=new JCheckBox("Data");
        checkDate.setSelected(false);
        checkDate.setBounds(100,150,100,20);
        checkDate.addActionListener(e->{
            txtData.setEditable(checkDate.isSelected());
        });

        JButton btnSave=new JButton("Save");
        btnSave.setBounds(50,250,80,30);
        btnSave.addActionListener(e->{
            String genre=null,artistName=null;
            int date=0;

            int index=playListsName.getSelectedIndex();
            boolean check=false;

            if(checkGenre.isSelected()){
                check=true;
                genre=txtGenre.getText();
            }
            if(checkArtistName.isSelected()){
                check=true;
                artistName=txtArtistName.getText();
            }
            if(checkDate.isSelected()){
                try {
                    date=Integer.parseInt(txtData.getText());
                    check=true;
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Enter number in data text field");
                }
            }

            if(check) {
                MyLinkList<Music> newList = playLists.get(index).getMusicByFilter(genre, artistName, date);
                PlayList p = new PlayList(newList, playLists.get(index).getNameByFilter(genre, artistName, date));
                playLists.addLast(p);

                values.removeAllElements();
                addMusicList(values, newList);

                filterFrame.dispose();
                JOptionPane.showMessageDialog(null,"Play List add");
            }
        });

        JButton btnBack=new JButton("Back");
        btnBack.setBounds(150,250,80,30);
        btnBack.addActionListener(e->{
            filterFrame.dispose();
        });

        filterFrame.add(checkGenre);
        filterFrame.add(txtGenre);
        filterFrame.add(checkArtistName);
        filterFrame.add(txtArtistName);
        filterFrame.add(checkDate);
        filterFrame.add(txtData);
        filterFrame.add(btnSave);
        filterFrame.add(btnBack);
        filterFrame.setVisible(true);
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
