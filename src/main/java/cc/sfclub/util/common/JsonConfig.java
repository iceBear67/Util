package cc.sfclub.util.common;

import cc.sfclub.util.Util;
import lombok.SneakyThrows;

public class JsonConfig {
    private transient String root;
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
        SimpleFile.write(root + "/" + getConfigName(), Util.getGson().toJson(this));
    }

    /**
     * Reload Config
     */
    @SneakyThrows
    public JsonConfig reloadConfig() {
        String confText = SimpleFile.readAsString(root + "/" + getConfigName());
        JsonConfig pluginConfig = Util.getGson().fromJson(confText, this.getClass());
        pluginConfig.root = root;
        return pluginConfig;
    }
}
