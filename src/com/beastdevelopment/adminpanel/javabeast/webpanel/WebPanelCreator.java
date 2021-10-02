package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class WebPanelCreator {


    public static String[] loadPanel(String name, String[] args) {
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.instance.getDataFolder() + "/" + name + ".yml"));

            HashMap<String, String> ARGS = new HashMap<>();

            for(String s:args) {
                String[] d = s.split("=");
                if(!d[0].equals("text")&&!d[0].equals("list")&&!d[0].equals("cmd")) {
                    ARGS.put(d[0], d[1]);
                }
            }

            StringBuilder linkBuilder = new StringBuilder();

            StringBuilder builder = new StringBuilder();
            int lines = config.getInt("lineCount");

            for(int i = 1; i < lines+1; i++) {
                String line = config.getString("line."+i);
                String[] content = line.split(";");

                for(String object:content) {
                    String[] objData = object.split(",");
                    String type = objData[0];

                    switch (type) {
                        case "button": {
                            String argsLength = "";
                            String id = "";
                            String color = "";
                            String _name = "";
                            String cmd = "";
                            String info = "default";

                            for (String s : objData) {
                                String[] _s = s.split("=");

                                switch (_s[0]) {
                                    case "id":
                                        id = "id_" + _s[1];
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
                                        break;
                                    case "info":
                                        info = _s[1];
                                        break;
                                }

                            }


                            if(!cmd.startsWith("!LISTEN!")) {
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

                                StringBuilder currentLink = new StringBuilder();

                                if(info.equals("default")) {
                                    currentLink.append("var ")
                                            .append(id)
                                            .append(" = \"/<!-- file -->?usr=<!-- usr -->&pw=<!-- pw -->&cmd=")
                                            .append(cmd);


                                    int length = Integer.parseInt(argsLength);

                                    for (int _i = 0; _i < length; _i++) {
                                        currentLink.append("_arg")
                                                .append(_i);
                                    }

                                    for (String key : ARGS.keySet()) {
                                        if (!currentLink.toString().contains(key + "=")) {
                                            currentLink.append("&").append(key).append("=").append(ARGS.get(key));
                                        }
                                    }

                                }else  {
                                    String[] infoData = info.split(":");

                                    currentLink.append("var ")
                                            .append(id)
                                            .append(" = \"/<!-- file -->?usr=<!-- usr -->&pw=<!-- pw -->");

                                    int _i = 0;
                                    for (String _info:infoData) {
                                        currentLink.append("&").append(_info).append("=").append("arg")
                                                .append(_i);
                                        _i++;
                                    }

                                    for (String key : ARGS.keySet()) {
                                        if (!currentLink.toString().contains(key + "=")) {
                                            currentLink.append("&").append(key).append("=").append(ARGS.get(key));
                                        }
                                    }

                                }
                                currentLink.append("\";\n");

                                linkBuilder.append(currentLink);
                            }else{
                                builder.append("<button onclick=\"toListener('")
                                        .append(_name)
                                        .append("',")
                                        .append("'")
                                        .append(id)
                                        .append("')\" id='")
                                        .append(id)
                                        .append("' class='button_")
                                        .append(color)
                                        .append("'> ")
                                        .append(_name)
                                        .append(" </button> ");

                                StringBuilder currentLink = new StringBuilder();

                                currentLink.append("var ")
                                        .append(id)
                                        .append(" = \"/<!-- file -->?usr=<!-- usr -->&pw=<!-- pw -->&list=listener&text=txt");

                                for(String key:ARGS.keySet()) {
                                    if(!currentLink.toString().contains(key+"=")) {
                                        currentLink.append("&").append(key).append("=").append(ARGS.get(key));
                                    }
                                }

                                currentLink.append("\";\n");
                                linkBuilder.append(currentLink);
                            }


                            break;
                        }
                        case "input": {
                            String id = "";
                            String _name = "";
                            String arg = "";
                            String color = "";

                            for (String s : objData) {
                                String[] _s = s.split("=");

                                switch (_s[0]) {
                                    case "id":
                                        id = "id_" + _s[1];
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
                                    .append(_name).append("\"");

                            if(ARGS.containsKey(_name)) {
                                builder.append(" value=\"").append(ARGS.get(_name)).append("\"");
                            }

                            builder.append(">");

                            break;
                        }
                        case "area": {
                            String id = "";
                            String _name = "";
                            String color = "";

                            for (String s : objData) {
                                String[] _s = s.split("=");

                                switch (_s[0]) {
                                    case "id":
                                        id = "id_" + _s[1];
                                        break;
                                    case "name":
                                        _name = _s[1];
                                        break;
                                    case "color":
                                        color = _s[1];
                                        break;
                                }

                            }



                            builder.append(" <textarea class='area_")
                                    .append(color)
                                    .append("' id=\"area_")
                                    .append(id)
                                    .append("\" name=\"")
                                    .append(_name)
                                    .append("\" rows=\"4\" cols=\"50\" maxlength=\"20000\">");

                            builder.append("\n");

                            if (Main.instance.getPanel().textHandler.containsKey(_name)) {

                                String text = Main.instance.getPanel().textHandler.get(_name).generateText(id, args);
                                builder.append(text);

                            } else
                                builder.append("NO TEXT HANDLER!");

                            builder.append("</textarea>");
                            builder.append("<br>");

                            break;
                        }
                        case "empty": {
                            builder.append("<br><br>");
                        }
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
