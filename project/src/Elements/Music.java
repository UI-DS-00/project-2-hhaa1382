package Elements;

public class Music implements Comparable<Music>{
    private String artistName;
    private String trackName;
    private int releaseDate;
    private String genre;
    private int len;
    private String topic;

    public Music(String artistName, String trackName, int releaseDate, String genre, int len, String topic) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.len = len;
        this.topic = topic;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString(){
        return "Track name : "+this.trackName+"   ---   artist : "+this.artistName;
    }

    @Override
    public int compareTo(Music o) {
        if(this.artistName.compareTo(o.getArtistName())>0){
            return 1;
        }
        else if(this.artistName.compareTo(o.getArtistName())<0){
            return -1;
        }
        else{
            if(this.trackName.compareTo(o.getTrackName())>0){
                return 1;
            }
            else if(this.trackName.compareTo(o.getTrackName())<0){
                return -1;
            }
            else{
                if(this.getReleaseDate()>o.getReleaseDate()){
                    return 1;
                }
                else if(this.getReleaseDate()<o.getReleaseDate()){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        }
    }
}
