package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WebPanelCreator {

    public static String loadPanel(String name) {
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.instance.getDataFolder() + "/" + name + ".yml"));

            StringBuilder builder = new StringBuilder();
            int lines = config.getInt("lineCount");

            for(int i = 1; i < lines+1; i++) {
                String line = config.getString("line."+i);
                String[] content = line.split(";");
                for(String object:content) {
                    String[] objData = object.split(",");
                    String objName = "";
                    String objCmd = "";
                    String objColor = "";

                    for(String s:objData) {
                        if(s.startsWith("name")) {
                            objName = s.split("=")[1];
                        }else if(s.startsWith("cmd")) {
                            objCmd = s.split("=")[1];
                        }else if(s.startsWith("color")){
                            objColor = s.split("=")[1];
                        }
                    }

                    builder.append(" <a href=\"http://localhost:<!-- port -->/?usr=<!-- usr -->&pw=<!-- pw -->&cmd=")
                            .append(objCmd.replace(" ", "_"))
                            .append("\"><button class=\"button_")
                            .append(objColor)
                            .append("\">")
                            .append(objName)
                            .append("</button></a> ");

                }

                builder.append("<br>");
                builder.append("<br>");
            }

            return builder.toString();

        }catch (NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
