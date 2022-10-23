package Pages;

import Data.Read;
import Data.Write;
import Elements.Music;
import Elements.MyLinkList;
import Elements.PlayList;

import javax.swing.*;
import java.sql.SQLException;

public class MusicListPart extends JFrame {
    private MyLinkList<Music> musics;
    private final MyLinkList<PlayList> playLists=new MyLinkList<>();

    private final JComboBox<String> playListsName=new JComboBox<>();

    private final DefaultListModel<String> values=new DefaultListModel<>();
    private JList<String> list;
    private final JScrollPane panel;

    private final Object checkPlay=new Object();

    PlayList likePlayList;
    PlayList dislikePlayList;

    public MusicListPart(MyLinkList<Music> allMusics) throws SQLException, ClassNotFoundException {
        this.setTitle("Music Player");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        addDefaultPlayLists(allMusics);
        addMusicList(values,allMusics);
        list=new JList<>(values);

        JButton btnSort=new JButton("Sort");
        btnSort.setBounds(10,10,100,20);
        btnSort.addActionListener(e->{
            values.removeAllElements();
            MyLinkList<Music> sortedMusics=playLists.get(playListsName.getSelectedIndex()).getMusics();
            sortedMusics.sort();
            addMusicList(values,sortedMusics);
        });

        JButton btnFilter=new JButton("Filter");
        btnFilter.setBounds(150,10,100,20);
        btnFilter.addActionListener(e->{
            setFilter();
        });

        JButton btnAddPlayList=new JButton("Add Play List");
        btnAddPlayList.setBounds(530,40,150,30);
        btnAddPlayList.addActionListener(e->{
            try {
                String name = JOptionPane.showInputDialog("Enter name for play list :");
                if (name != null) {
                    if (checkPlayListName(name)) {
                        playListsName.addItem(name);
                        playLists.addLast(new PlayList(name));
                        JOptionPane.showMessageDialog(null, "Play list add");
                        Write.writePlayList(name);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Play list name is duplicated");
                    }
                }
            }
            catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton btnRemovePlayList=new JButton("Remove Play List");
        btnRemovePlayList.setBounds(530,80,150,30);
        btnRemovePlayList.addActionListener(e->{
            try {
                int index = playListsName.getSelectedIndex();
                if (index != -1) {
                    String name=(String) playListsName.getSelectedItem();

                    if(!name.equals("All musics") && !name.equals("Like") && !name.equals("Dislike")) {
                        playLists.remove(index);

                        Write.deletePlayList((String) playListsName.getSelectedItem());

                        playListsName.removeItemAt(index);
                        values.removeAllElements();
                    }
                }
            }
            catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton btnMerge=new JButton("Merge");
        btnMerge.setBounds(530,140,150,30);
        btnMerge.addActionListener(e->{
            try {
                mergePart();
            }
            catch (ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
            }
        });

        JButton shuffleMerge=new JButton("Shuffle merge");
        shuffleMerge.setBounds(530,180,150,30);
        shuffleMerge.addActionListener(e->{
            shuffleMergePart();
        });

        playListsName.setBounds(530,240,150,30);

        JButton btnSwitch=new JButton("Switch");
        btnSwitch.setBounds(530,280,150,30);
        btnSwitch.addActionListener(e->{
            int index=playListsName.getSelectedIndex();
            if(index!=-1){
                values.removeAllElements();
                addMusicList(values,playLists.get(index).getMusics());
                setMusicList(playLists.get(index));
            }
        });

        JButton btnAddMusic=new JButton("Add Music");
        btnAddMusic.setBounds(530,340,150,30);
        btnAddMusic.addActionListener(e->{
            try {
                String[] valuesName = new String[playLists.size()];
                for (int i = 0; i < playLists.size(); i++) {
                    valuesName[i] = playLists.get(i).getName();
                }

                String ans = (String) JOptionPane.showInputDialog(null, "Choose a play list", "Play lists",
                        JOptionPane.QUESTION_MESSAGE, null, valuesName, null);

                if (ans != null) {
                    PlayList temp = playLists.get(getIndex(ans, valuesName));
                    temp.addMusic(musics.get(list.getSelectedIndex()));
                    Write.writeMusics(temp.getName(), musics.get(list.getSelectedIndex()).getTrackName());
                }
            }
            catch (ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
            }
        });

        JButton btnRemoveMusic=new JButton("Remove music");
        btnRemoveMusic.setBounds(530,380,150,30);
        btnRemoveMusic.addActionListener(e->{
            try {
                int index = playListsName.getSelectedIndex();
                if (index != -1) {
                    String name=(String) playListsName.getSelectedItem();

                    if(!name.equals("All musics")) {
                        PlayList p = playLists.get(index);
                        Write.deletePlayListMusic(name,p.getMusics().get(list.getSelectedIndex()).getTrackName());

                        p.removeMusic(list.getSelectedIndex());
                        values.removeElementAt(list.getSelectedIndex());
                    }
                }
            }
            catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        panel=new JScrollPane(list);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(10,40,500,400);

        JLabel musicPlayed=new JLabel("");
        musicPlayed.setBounds(10,550,500,40);

        JButton btnPlay=new JButton("Play");
        btnPlay.setBounds(100,500,100,30);
        btnPlay.addActionListener(e->{
            String value = list.getSelectedValue();
            musicPlayed.setText(value);
        });

        JButton btnLike=new JButton("Like");
        btnLike.setBounds(40,600,100,20);
        btnLike.addActionListener(e->{
            try {
                likePart();
            }
            catch (ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
            }
        });

        JButton btnDislike=new JButton("DisLike");
        btnDislike.setBounds(160,600,100,20);
        btnDislike.addActionListener(e->{
            try {
                disLikePart();
            }
            catch (ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
            }
        });

        this.add(btnSort);
        this.add(btnFilter);

        this.add(btnAddPlayList);
        this.add(btnRemovePlayList);

        this.add(btnMerge);
        this.add(shuffleMerge);

        this.add(playListsName);
        this.add(btnSwitch);

        this.add(btnAddMusic);
        this.add(btnRemoveMusic);

        this.getContentPane().add(panel);

        this.add(btnPlay);
        this.add(musicPlayed);
        this.add(btnLike);
        this.add(btnDislike);

        this.setVisible(true);
    }

    private void likePart() throws ClassNotFoundException,SQLException{
        int index=list.getSelectedIndex();
        if(index!=-1) {
            MyLinkList<Music> musics = playLists.get(playListsName.getSelectedIndex()).getMusics();
            Music m = musics.get(index);

            MyLinkList<Music> likePlaylistMusics = likePlayList.getMusics();
            if (likePlaylistMusics.contain(m)) {
                for (int i = 0; i < likePlaylistMusics.size(); i++) {
                    if (likePlaylistMusics.get(i) == m) {
                        likePlaylistMusics.remove(i);
                        if(playListsName.getSelectedItem().equals("Like")){
                            Write.deletePlayListMusic("Like",m.getTrackName());
                            values.removeElementAt(i);
                        }
                        break;
                    }
                }
            }
            else {
                likePlaylistMusics.addLast(m);
                Write.writeMusics("Like",m.getTrackName());
            }
        }
    }

    private void disLikePart() throws ClassNotFoundException,SQLException{
        int index=list.getSelectedIndex();
        if(index!=-1) {
            MyLinkList<Music> musics = playLists.get(playListsName.getSelectedIndex()).getMusics();
            Music m = musics.get(index);

            MyLinkList<Music> dislikePlaylistMusics = dislikePlayList.getMusics();
            if (dislikePlaylistMusics.contain(m)) {
                for (int i = 0; i < dislikePlaylistMusics.size(); i++) {
                    if (dislikePlaylistMusics.get(i) == m) {
                        dislikePlaylistMusics.remove(i);
                        if(playListsName.getSelectedItem().equals("Dislike")){
                            values.removeElementAt(i);
                            Write.deletePlayListMusic("Dislike",m.getTrackName());
                        }
                        break;
                    }
                }
            }
            else {
                dislikePlaylistMusics.addLast(m);
                Write.writeMusics("Dislike",m.getTrackName());
            }
        }
    }

    private void mergePart() throws ClassNotFoundException,SQLException{
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

            String name=playLists.get(firstIndex).getName()+" & "+playLists.get(secondIndex).getName();
            PlayList p=new PlayList(newList,name);
            Write.writePlayList(name);

            for(int i=0;i<firstList.size();i++){
                newList.addLast(firstList.get(i));
                Write.writeMusics(name,firstList.get(i).getTrackName());
            }

            for(int i=0;i<secondList.size();i++){
                if(!newList.contain(secondList.get(i))) {
                    newList.addLast(secondList.get(i));
                    Write.writeMusics(name,secondList.get(i).getTrackName());
                }
            }

            playLists.addLast(p);
            playListsName.addItem(name);
            values.removeAllElements();
            addMusicList(values,newList);

            JOptionPane.showMessageDialog(null,"Play lists merged");
        }
    }

    private void addDefaultPlayLists(MyLinkList<Music> allMusics) throws SQLException, ClassNotFoundException {
        musics=allMusics;

        playLists.addLast(new PlayList(allMusics,"All musics"));
        playListsName.addItem("All musics");

        MyLinkList<PlayList> tempPlayLists= Read.readPlayLists(allMusics);

        for(int i=0;i<tempPlayLists.size();i++){
            playLists.addLast(tempPlayLists.get(i));
            playListsName.addItem(tempPlayLists.get(i).getName());

            if(tempPlayLists.get(i).getName().equals("Like")){
                likePlayList=tempPlayLists.get(i);
            }
            else if(tempPlayLists.get(i).getName().equals("Dislike")){
                dislikePlayList=tempPlayLists.get(i);
            }
        }
    }

    private void shuffleMergePart(){
        JFrame shuffleFrame=new JFrame("Shuffle");
        shuffleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shuffleFrame.setSize(300,200);
        shuffleFrame.setResizable(false);
        shuffleFrame.setLocationRelativeTo(null);
        shuffleFrame.setLayout(null);

        JComboBox<String> savedValues=new JComboBox<>();
        savedValues.setBounds(50,10,200,40);

        JButton add=new JButton("Add play list");
        add.setBounds(70,60,150,30);
        add.addActionListener(e->{
            String[] values=new String[playListsName.getItemCount()];
            for(int i=0;i<values.length;i++){
                values[i]=playListsName.getItemAt(i);
            }

            String ans=(String) JOptionPane.showInputDialog(null,"Choose a play list",
                    "Play lists",JOptionPane.QUESTION_MESSAGE,null,values,null);

            if(ans!=null){
                int index=getIndex(ans,values);
                savedValues.addItem(values[index]);
            }
        });

        JButton save=new JButton("Save");
        save.setBounds(25,120,100,30);
        save.addActionListener(e->{
            try {
                MyLinkList<Music> musics = new MyLinkList<>();

                String name = getShuffleListName(savedValues);
                PlayList newPlayList = new PlayList(musics, name);
                Write.writePlayList(name);

                for (int i = 0; i < savedValues.getItemCount(); i++) {
                    PlayList p = findPlayList(savedValues.getItemAt(i));
                    if (p != null) {
                        MyLinkList<Music> playListMusics = p.getMusics();
                        for (int j = 0; j < playListMusics.size(); j++) {
                            Music m = playListMusics.get(j);
                            if (!musics.contain(m)) {
                                musics.addLast(m);
                                Write.writeMusics(p.getName(),m.getTrackName());
                            }
                        }
                    }
                }

                playLists.addLast(newPlayList);
                playListsName.addItem(name);
                shuffleFrame.dispose();
            }
            catch (ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
            }
        });

        JButton back=new JButton("Back");
        back.setBounds(175,120,100,30);
        back.addActionListener(e->{
            shuffleFrame.dispose();
        });

        shuffleFrame.add(savedValues);
        shuffleFrame.add(add);
        shuffleFrame.add(save);
        shuffleFrame.add(back);
        shuffleFrame.setVisible(true);
    }

    private String getShuffleListName(JComboBox<String> values){
        StringBuilder temp=new StringBuilder();
        for(int i=0;i<values.getItemCount();i++){
            temp.append(values.getItemAt(i));
            if(i!=values.getItemCount()-1){
                temp.append(" & ");
            }
        }
        return temp.toString();
    }

    private PlayList findPlayList(String playListName){
        for(int i=0;i<playListsName.getItemCount();i++){
            if(playListsName.getItemAt(i).equals(playListName)){
                return playLists.get(i);
            }
        }
        return null;
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
                playListsName.addItem(p.getName());

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
