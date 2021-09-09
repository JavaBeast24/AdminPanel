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
import java.util.List;

public class WebPanel {

    private final HttpServer server;
    public static int port;

    public WebPanel(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        Bukkit.getConsoleSender().sendMessage("§7[§2AdminPanel§7]§2 WebPanel started at port §6"+port+"§2.");
        server.createContext("/", new RootHandler());
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
            String[] data = he.getRequestURI().toString().split("\\?")[1].split("&");

            String usr = "";
            String pw = "";

            List<String> actions = new ArrayList<>();

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
                }
            }

            boolean success = Account.login(usr, pw);
            if(success) {
                success = false;
                for(Player p:Bukkit.getOnlinePlayers()) {
                    if(p.getName().equals(usr)) {
                        success = true;
                    }
                }
            }

            if(success) {

                for(String cmd:actions) {
                    Action.performCommand(cmd, usr);
                }

                File file = new File(Main.instance.getDataFolder() + "/root.html");
                byte[] content = Files.readAllBytes(Paths.get(file.getPath()));
                String _content = new String(content)
                        .replace("<!-- body -->", WebPanelCreator.loadPanel("root"))
                        .replace("<!-- playerCount -->", Bukkit.getOnlinePlayers().size() + "")
                        .replace("<!-- port -->", port + "")
                        .replace("<!-- usr -->", usr)
                        .replace("<!-- pw -->", pw);

                he.sendResponseHeaders(200, _content.length());
                OutputStream os = he.getResponseBody();
                os.write(_content.getBytes(StandardCharsets.UTF_8));
                os.close();
            }else {
                String _content = "login failed! (maybe your usrname or password was wrong or you are not playing at the moment)";
                he.sendResponseHeaders(200, _content.length());
                OutputStream os = he.getResponseBody();
                os.write(_content.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

}
