import Data.Read;
import Data.Write;
import Elements.*;
import Pages.MusicListPart;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        MyLinkList<Music> musics= Read.readMusics();
//        MyLinkList<PlayList> playLists=Read.readPlayLists(musics);
//        System.out.println(playLists.size());
        new MusicListPart(musics);
    }
}
