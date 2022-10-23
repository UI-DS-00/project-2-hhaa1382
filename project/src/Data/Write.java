package Data;

import Elements.Music;
import Elements.PlayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Write {
    private static final String url="jdbc:mysql://localhost/music_play_project";
    private static final String username="root";
    private static final String password="hhaa1382";

    public static void writePlayList(String name) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        String sql="insert into play_lists values('"+name+"')";
        Statement st=connect.prepareStatement(sql);
        st.execute(sql);
    }

    public static void writeMusics(String playListName,String musicName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        String sql="insert into musics_list values('"+playListName+"','"+musicName+"')";
        Statement st=connect.prepareStatement(sql);
        st.execute(sql);
    }

    public static void deletePlayList(String playListName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        String sqlPlayLists="delete from play_lists where names='"+playListName+"'";
        String sqlPlayListMusic="delete from musics_list where play_list_name='"+playListName+"'";

        Statement st=connect.prepareStatement(sqlPlayLists);
        st.execute(sqlPlayLists);

        st=connect.prepareStatement(sqlPlayListMusic);
        st.execute(sqlPlayListMusic);
    }

    public static void deletePlayListMusic(String playListName,String musicName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        String sql="delete from musics_list where play_list_name='"+playListName+"' AND music_name='"+musicName+"'";
        Statement st=connect.prepareStatement(sql);
        st.execute(sql);
    }
}
