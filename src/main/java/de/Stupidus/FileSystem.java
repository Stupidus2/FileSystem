package de.Stupidus;

import jdk.internal.org.jline.utils.InputStreamReader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileSystem {

    private File file;
    private YamlConfiguration yaml;

    public FileSystem(String fileName, Plugin plugin) throws IOException {

        File tempDir = new File(plugin.getDataFolder().getAbsolutePath());
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        file = new File(plugin.getDataFolder().getAbsolutePath(), fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        yaml = YamlConfiguration.loadConfiguration(new InputStreamReader(java.nio.file.Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
    }

    public HashMap<String, Object> read() {
        HashMap<String, Object> content = new HashMap<>();
        for (String location : yaml.getKeys(false)) {
            content.put(location, yaml.get(location));
        }
        return content;
    }

    public Object read(String location) {
        return yaml.get(location);
    }

    public void write(String location, Object content) {
        try {
            yaml.set(location, content);
            yaml.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clear() {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String location) {
        this.write(location, (Object) null);
    }

    public void delete() {
        file.delete();
    }

    public String getName() {
        return file.getName();
    }

    public String getPath() {
        return file.getPath();
    }

    public File getFile() {
        return file;
    }

    public String getParent() {
        return file.getParent();
    }

}
