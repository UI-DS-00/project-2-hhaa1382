import Data.Read;
import Elements.*;
import Pages.MusicListPart;

import java.io.IOException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {

        MyLinkList<Music> musics= Read.readMusics();
        new MusicListPart(musics);
//        musics.sort();
//        for(int i=0;i<musics.size();i++){
//            Music m=musics.get(i);
//            System.out.println("Artist name : "+m.getArtistName()+"   track name : "+m.getTrackName()+"   date : "+m.getReleaseDate());
//        }
        musics.sort();
        new MusicListPart(musics);
    }
}
