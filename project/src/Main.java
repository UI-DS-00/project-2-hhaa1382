import Data.Read;
import Elements.*;
import Pages.MusicListPart;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MyLinkList<Music> musics= Read.readMusics();
//        for(int i=0;i<musics.size();i++){
//            Music m=musics.get(i);
//            System.out.println("Artist name : "+m.getArtistName()+"   track name : "+m.getTrackName()+"   date : "+m.getReleaseDate());
//        }
        System.out.println(musics.size());
        musics.remove(1);
        System.out.println(musics.size());
        new MusicListPart(musics);

    }
}
