import Data.Read;
import Data.Write;
import Elements.*;
import Pages.MusicListPart;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        MyLinkList<Music> musicsList=new MyLinkList<>();
        musicsList.addLast(new Music("Hamid","Fuck",1401,"Pop",124,"Pussy"));
        MyLinkList<PlayList> p=new MyLinkList<>();
        p.addLast(new PlayList(musicsList,"fav1"));
        Write.writePlayLists(p);
        Write.writeMusics(p);

//        MyLinkList<Music> musics= Read.readMusics();
//        new MusicListPart(musics);
    }
}
