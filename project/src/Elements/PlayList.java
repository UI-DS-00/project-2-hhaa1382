package Elements;

public class PlayList {
    private MyLinkList<Music> musics;
    private final String name;

    public PlayList(MyLinkList<Music> musics,String name){
        this.musics=musics;
        this.name=name;
    }

    public PlayList(String name){
        this.name=name;
        this.musics=new MyLinkList<>();
    }

    public void addMusic(Music m){
        musics.addLast(m);
    }

    public void removeMusic(int index){
        musics.remove(index);
    }

    public String getName(){
        return this.name;
    }

    public MyLinkList<Music> getMusics(){
        return this.musics;
    }
}
