package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class WebPanel {

    private final HttpServer server;
    public static int port;

    public final HashMap<String, TextListener> listeners = new HashMap<>();
    public final HashMap<String, AreaTextHandler> textHandler = new HashMap<>();

    public WebPanel(int port, String[] panels) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        Bukkit.getConsoleSender().sendMessage("§7[§2AdminPanel§7]§2 WebPanel started at port §6"+port+"§2.");
        server.createContext("/", new RootHandler());
        for(String panel:panels) {
            server.createContext(panel, new RootHandler());
        }
        WebPanel.port = port;
        server.setExecutor(null);
        server.start();
    }

    public HttpServer getServer() {
        return server;
    }

    private static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            if(he.getRequestURI().toString().equals("/favicon.ico")) {
                if(new File(Main.instance.getDataFolder()+"/favicon.ico").exists()) {
                    byte[] icon = Files.readAllBytes(Paths.get(Main.instance.getDataFolder() + "/favicon.ico"));
                    he.sendResponseHeaders(200, icon.length);
                    OutputStream os = he.getResponseBody();
                    os.write(icon);
                    os.flush();
                    return;
                }
            }

            String[] _page = he.getRequestURI().toString().split("\\?")[0].split("/");
            String page = null;
            if(_page.length > 0) {
                page = _page[_page.length - 1];
            }

            if(page == null || Objects.equals(page, "")) {
                page = "root";
            }

            String[] data = he.getRequestURI().toString().split("\\?")[1].split("&");

            String usr = "";
            String pw = "";

            List<String> actions = new ArrayList<>();
            List<String> pageInfo = new ArrayList<>();
            HashMap<String, String> _pageInfo = new HashMap<>();
            String listener = "";
            String text = "";

            for(String s:data) {
                String[] content = s.split("=");
                switch(content[0]) {
                    case "cmd":
                        actions.add(content[1]);
                        break;
                    case "usr":
                        usr = content[1];
                        break;
                    case "pw":
                        pw = content[1];
                        break;
                    case "list":
                        listener = content[1];
                        break;
                    case "text":
                        text = content[1];
                        break;
                    default:
                        break;
                }

                pageInfo.add(content[0]+"="+content[1]);
                _pageInfo.put(content[0], content[1]);
            }

            boolean success = Account.login(usr, pw, page);
            if(success) {
                success = false;
                for(Player p:Bukkit.getOnlinePlayers()) {
                    if(p.getName().equals(usr)) {
                        success = true;
                    }
                }
            }

            if(success) {

                if(Main.instance.getPanel().listeners.containsKey(listener)) {
                    Main.instance.getPanel().listeners.get(listener).onListen(listener, text, _pageInfo);
                }

                for(String cmd:actions) {
                    Action.performCommand(cmd, usr);
                }

                File file = new File(Main.instance.getDataFolder() + "/root.html");
                byte[] content = Files.readAllBytes(Paths.get(file.getPath()));

                String[] configCont = WebPanelCreator.loadPanel(page, pageInfo.toArray(new String[0]));

                if(configCont == null) {
                    he.sendResponseHeaders(200, "ERROR".length());
                    OutputStream os = he.getResponseBody();
                    os.write("ERROR".getBytes(StandardCharsets.UTF_8));
                    os.close();
                    return;
                }

                String player = "_";
                if(_pageInfo.containsKey("player")) {
                    player = _pageInfo.get("player");
                }

                String _content = new String(content)
                        .replace("<!-- id -->", configCont[1])
                        .replace("<!-- body -->", configCont[0])
                        .replace("<!-- playerCount -->", Bukkit.getOnlinePlayers().size() + "")
                        .replace("<!-- port -->", port + "")
                        .replace("<!-- usr -->", usr)
                        .replace("<!-- pw -->", pw)
                        .replace("<!-- file -->", page)
                        .replace("PLAYER", player);

                he.sendResponseHeaders(200, _content.length());
                OutputStream os = he.getResponseBody();
                os.write(_content.getBytes(StandardCharsets.UTF_8));
                os.close();
            }else {
                String _content = "login failed! wrong username?\n" +
                        "wrong password?\n" +
                        "no permission?\n" +
                        "not playing on the server?\n" +
                        "panel not existing?\n";
                he.sendResponseHeaders(200, _content.length());
                OutputStream os = he.getResponseBody();
                os.write(_content.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

}
