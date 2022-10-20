package Data;

import Elements.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {
    public static MyLinkList<Music> readMusics() throws IOException {
        MyLinkList<Music> linkList=new MyLinkList<>();
        BufferedReader reader=new BufferedReader(new FileReader("musics.csv"));

        reader.readLine();
        String line=reader.readLine();
        while (line!=null){
            String[] temp=line.split(",");
            temp=getCorrectValues(temp);
            linkList.addLast(getMusic(temp));
            line=reader.readLine();
        }

        return linkList;
    }

    private static String getToString(String[] values){
        StringBuilder temp=new StringBuilder();
        for(String s:values){
            temp.append(s+" , ");
        }
        return temp.toString();
    }

    private static Music getMusic(String[] values){
        String artistName=values[0];
        String trackName=values[1];
        int releaseDate=Integer.parseInt(values[2]);
        String genre=values[3];
        int len=Integer.parseInt(values[4]);
        String topic=values[5];

        return new Music(artistName,trackName,releaseDate,genre,len,topic);
    }

    private static String[] getCorrectValues(String[] values){
        if(values.length==6){
            return values;
        }
        else{
            String[] newValues=new String[6];
            int counter=0;

            for(int i=0;i<newValues.length;i++){
                if(i==1){
                    newValues[i]=values[1]+","+values[2];
                    counter++;
                }
                else{
                    newValues[i]=values[i+counter];
                }
            }
            return newValues;
        }
    }
}
