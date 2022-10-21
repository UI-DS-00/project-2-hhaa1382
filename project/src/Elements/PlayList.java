package Elements;

public class PlayList {
    private MyLinkList<Music> musics;

    public void addMusic(Music m){
        musics.addLast(m);
    }
}
