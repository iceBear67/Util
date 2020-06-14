package cc.sfclub.util.common;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class SimpleFile {
    @SneakyThrows
    public static String readAsString(String pathToFile){
        BufferedReader br=new BufferedReader(new FileReader(pathToFile));
        String tmp;
        StringBuilder sb=new StringBuilder();
        while((tmp=br.readLine())!=null){
            sb.append(tmp);
        }
        return sb.toString();
    }
    @SneakyThrows
    public static void write(String pathToFile,String context){
        BufferedWriter bw=new BufferedWriter(new FileWriter(pathToFile));
        bw.write(context);
        bw.flush();
        bw.close();
    }
}
