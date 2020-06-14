package cc.sfclub.util.common;

import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.io.*;

public class JsonConfig {
    private transient String root;
    private static transient Gson gson=new Gson();
    public JsonConfig(String rootDir) {
        root = rootDir;
    }

    public String getConfigName() {
        return "config.json";
    }

    /**
     * Save Config to config.json
     */
    @SneakyThrows
    public void saveConfig() {
            byte[] bWrite = gson.toJson(this).getBytes();
            File conf = new File(root + "/" + getConfigName());
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(conf));
            os.write(bWrite);
            os.flush();
            os.close();
    }

    /**
     * Reload Config
     */
    @SneakyThrows
    public JsonConfig reloadConfig() {
            BufferedInputStream f = new BufferedInputStream(new FileInputStream(root + "/" + getConfigName()));
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            JsonConfig pluginConfig = gson.fromJson(confText.toString(), this.getClass());
            pluginConfig.root = root;
            return pluginConfig;
    }
}
