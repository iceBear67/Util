package cc.sfclub.util.common;

import lombok.SneakyThrows;

import java.io.*;

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
    public static void write(String pathToFile, String context) {
        BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile));
        bw.write(context);
        bw.flush();
        bw.close();
    }

    public static boolean exists(String pathtoFIle) {
        File file = new File(pathtoFIle);
        return file.exists();
    }

    @SneakyThrows
    public static boolean touch(String pathtoFIle) {
        File file = new File(pathtoFIle);
        return file.createNewFile();
    }
}
