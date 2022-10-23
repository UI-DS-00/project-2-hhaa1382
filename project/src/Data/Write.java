package Data;

import Elements.Music;
import Elements.MyLinkList;
import Elements.PlayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Write {
    private static final String url="jdbc:mysql://localhost/music_play_project";
    private static final String username="root";
    private static final String password="hhaa1382";

    public static void writePlayLists(MyLinkList<PlayList> playLists) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        StringBuilder sql=new StringBuilder();
        Statement st;

        for(int i=0;i<playLists.size();i++){
            sql.append("insert into play_lists values('"+playLists.get(i).getName()+"')");
            st=connect.prepareStatement(sql.toString());
            st.execute(sql.toString());

            int size=sql.length();
            sql.delete(0,size);
        }
    }

    public static void writeMusics(MyLinkList<PlayList> playLists) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect= DriverManager.getConnection(url,username,password);

        StringBuilder sql=new StringBuilder();
        Statement st;

        for(int i=0;i<playLists.size();i++){
            sql.append("insert into musics_list values('"+playLists.get(i).getName()+"','");
            int size1=sql.length();

            MyLinkList<Music> musics=playLists.get(i).getMusics();
            for(int j=0;j<musics.size();j++){
                sql.append(musics.get(i).getTrackName()+"')");
                st=connect.prepareStatement(sql.toString());
                st.execute(sql.toString());

                int size2=sql.length();
                sql.delete(size1,size2);
            }

            sql.delete(0,size1);
        }
    }
}
