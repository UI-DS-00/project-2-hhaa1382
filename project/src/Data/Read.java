package Data;

import Elements.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Read {
    public static MyLinkList<Music> readMusics() throws IOException {
        MyLinkList<Music> linkList=new MyLinkList<>();
        BufferedReader reader=new BufferedReader(new FileReader("musics.csv"));

        reader.readLine();
        String line=reader.readLine();
        while (line!=null){
            String[] temp=line.split(",");
            temp=getCorrectValues(temp);
            linkList.addLast(getMusic(temp));
            line=reader.readLine();
        }

        return linkList;
    }

    public static MyLinkList<PlayList> readPlayLists(MyLinkList<Music> allMusics) throws ClassNotFoundException, SQLException {
        final String url="jdbc:mysql://localhost/music_play_project";
        final String username="root";
        final String password="hhaa1382";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        MyLinkList<PlayList> playLists=new MyLinkList<>();
        String sqlPlayLists="select * from play_lists";
        Statement st=connect.prepareStatement(sqlPlayLists);
        ResultSet rs=st.executeQuery(sqlPlayLists);

        while (rs.next()){
            String name=rs.getString("names");

            MyLinkList<Music> musics=new MyLinkList<>();
            String sqlPlayListMusics="select * from musics_list where play_list_name='"+name+"'";
            Statement tempSt=connect.prepareStatement(sqlPlayListMusics);
            ResultSet tempRs=tempSt.executeQuery(sqlPlayListMusics);

            while (tempRs.next()){
                musics.addLast(getMusicByName(tempRs.getString("music_name"),allMusics));
            }
            playLists.addLast(new PlayList(musics,name));
        }
        return playLists;
    }

    private static Music getMusicByName(String name,MyLinkList<Music> allMusics){
        for(int i=0;i<allMusics.size();i++){
            Music m=allMusics.get(i);
            if(m.getTrackName().equals(name)){
                return allMusics.get(i);
            }
        }
        return null;
    }

    private static Music getMusic(String[] values){
        String artistName=values[0];
        String trackName=values[1];
        int releaseDate=Integer.parseInt(values[2]);
        String genre=values[3];
        int len=Integer.parseInt(values[4]);
        String topic=values[5];

        return new Music(artistName,trackName,releaseDate,genre,len,topic);
    }

    private static String[] getCorrectValues(String[] values){
        if(values.length==6){
            return values;
        }
        else{
            String[] newValues=new String[6];
            int counter=0;

            for(int i=0;i<newValues.length;i++){
                if(i==1){
                    newValues[i]=values[1]+","+values[2];
                    counter++;
                }
                else{
                    newValues[i]=values[i+counter];
                }
            }
            return newValues;
        }
    }
}
