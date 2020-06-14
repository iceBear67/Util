package cc.sfclub.util.common;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Http {
    @SneakyThrows
    public static String get(String _url) {
        URL url = new URL(_url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        reader.close();
        return sb.toString();
    }
}
