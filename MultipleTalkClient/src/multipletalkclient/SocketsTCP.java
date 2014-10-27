package multipletalkclient;

import java.io.IOException;
import java.net.*;
import javax.swing.JOptionPane;

public class SocketsTCP {
    private String name;
    private Socket socket;
    private String host;
    private String port = "4545"; //4545 é o padrão do programa

    SocketsTCP() {
        name = JOptionPane.showInputDialog("Informe o seu nome:");
        host = JOptionPane.showInputDialog("Informe o IP do servidor:");
        try{
            socket = new Socket(host.trim(), Integer.parseInt(port.trim()));
        }catch (NumberFormatException | IOException err) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar-se ao servidor", "Erro", JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    public void close(){
        try {
            socket.close();
        } catch (Exception err) {
                JOptionPane.showMessageDialog(null, getName()+" Desconectou-se...", "Logout", JOptionPane.PLAIN_MESSAGE , null);
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
}