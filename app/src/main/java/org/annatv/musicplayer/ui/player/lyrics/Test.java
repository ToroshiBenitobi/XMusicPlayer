package org.annatv.musicplayer.ui.player.lyrics;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Test extends AppCompatActivity{
    public static void main(String[] args) {

    }

    /**
     * 从assets目录下读取歌词文件内容
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName){

        try {

            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufReader.readLine()) != null){

                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    //从assets目录下读取歌词文件内容
    String lrc = getFromAssets("test.lrc");
    //解析歌词构造器
    ILrcBuilder builder = new DefaultLrcBuilder();
    //解析歌词返回LrcRow集合
    List<LrcRow> rows = builder.getLrcRows(lrc);
}
