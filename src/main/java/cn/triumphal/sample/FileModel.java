package cn.triumphal.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileModel {
    public void saveToFile(String filename, String content){
        File file = new File(filename);
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
