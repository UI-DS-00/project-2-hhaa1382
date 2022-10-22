package Elements;

public class PlayList implements Comparable<PlayList>{
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

    public MyLinkList<Music> getMusicByFilter(String genre,String artistName,int date){
        MyLinkList<Music> newList=new MyLinkList<>();
        for(int i=0;i<musics.size();i++){
            Music m=musics.get(i);
            boolean check=true;
            if(genre!=null && !m.getGenre().equals(genre)){
                check=false;
            }
            if(artistName!=null && !m.getArtistName().equals(artistName)){
                check=false;
            }
            if(date!=0 && m.getReleaseDate()!=date){
                check=false;
            }

            if(check){
                newList.addLast(m);
            }
        }
        return newList;
    }

    public String getNameByFilter(String genre,String artistName,int date){
        String name=this.getName();
        if(genre!=null){
            name+=genre;
        }
        if(artistName!=null){
            name+=artistName;
        }
        if(date!=0){
            name+=date;
        }
        return name;
    }

    @Override
    public int compareTo(PlayList o) {
        return 0;
    }
}
