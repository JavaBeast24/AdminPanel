package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WebPanelCreator {

    public static String[] loadPanel(String name) {
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.instance.getDataFolder() + "/" + name + ".yml"));

            StringBuilder linkBuilder = new StringBuilder();

            StringBuilder builder = new StringBuilder();
            int lines = config.getInt("lineCount");

            for(int i = 1; i < lines+1; i++) {
                String line = config.getString("line."+i);
                String[] content = line.split(";");

                for(String object:content) {
                    String[] objData = object.split(",");
                    String type = objData[0];

                    if (type.equals("button")) {
                        String argsLength = "";
                        String id = "";
                        String color = "";
                        String _name = "";
                        String cmd = "";

                        for(String s:objData) {
                            String[] _s = s.split("=");

                            switch(_s[0]) {
                                case "id":
                                    id = "id_"+_s[1];
                                    break;
                                case "name":
                                    _name = _s[1];
                                    break;
                                case "cmd":
                                    cmd = _s[1].replace(" ", "_");
                                    break;
                                case "args":
                                    argsLength = _s[1];
                                    break;
                                case "color":
                                    color = _s[1];
                            }

                        }

                        builder.append(" <button ")
                                .append("onclick=\"passCommand(")
                                .append(argsLength)
                                .append(",'")
                                .append(id)
                                .append("')\" id='")
                                .append(id)
                                .append("' class='button_")
                                .append(color)
                                .append("'> ")
                                .append(_name)
                                .append("</button> ");

                        linkBuilder.append("var ")
                                .append(id)
                                .append(" = \"/?usr=<!-- usr -->&pw=<!-- pw -->&cmd=")
                                .append(cmd);

                        int length = Integer.parseInt(argsLength);

                        for(int _i = 0; _i < length; _i++) {
                            linkBuilder.append("_arg")
                                    .append(_i);
                        }

                        linkBuilder.append("\";\n");

                    } else if (type.equals("input")) {
                        String id = "";
                        String _name = "";
                        String arg = "";
                        String color = "";

                        for(String s:objData) {
                            String[] _s = s.split("=");

                            switch(_s[0]) {
                                case "id":
                                    id = "id_"+_s[1];
                                    break;
                                case "name":
                                    _name = _s[1];
                                    break;
                                case "arg":
                                    arg = _s[1];
                                    break;
                                case "color":
                                    color = _s[1];
                            }

                        }

                        builder.append(" <input class='input_")
                                .append(color)
                                .append("' type=\"text\" id=\"input_")
                                .append(id)
                                .append("_arg_")
                                .append(arg)
                                .append("\" name=\"")
                                .append(_name)
                                .append("\">");

                    }
                }

                builder.append("<br>");
                builder.append("<br>");
            }

            return new String[] {builder.toString(), linkBuilder.toString()};

        }catch (NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
