package multipletalkserver;

import java.net.*;
import javax.swing.JOptionPane;

public class SocketsTCP {
    private Socket socket;
    private String name;
    private String host;
    private String port = "4545"; //4545 é o padrão do programa

    SocketsTCP(){
        socket = null;
        name = null;
        host =  null;
        port = null;
    }
    
    public void close(){
        try {
            socket.close();
        } catch (Exception err) {
        }
    }
    public String getName(){
            return name;
    }
    public String getIp(){
            return host;
    }
    public String getPort(){
            return port;
    }
    public Socket getSocket(){
            return socket;
    }
    public void setName(String n){
            name = n;
    }
    public void setIp(String h){
            host = h;
    }
    public void setPort(String p){
            port = p;
    }
    public void setSocket(Socket s){
            socket = s;
    }
}