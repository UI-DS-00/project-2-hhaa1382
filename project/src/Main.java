import Data.Read;
import Elements.*;
import Pages.MusicListPart;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MyLinkList<Music> musics= Read.readMusics();
        new MusicListPart(musics);
    }
}
