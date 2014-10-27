package multipletalkserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class SimpleThread extends Thread{

    private SocketsTCP socket;
    private MultipleTalkServer window;
    private String user, msg;
      
    public SimpleThread(SocketsTCP skt, MultipleTalkServer server){
        super();
        socket = skt;
        window = server;
    }
    @Override
    public void run(){
        try{
            if(!window.IsFull()){
                socket.setName(recvStringMsg());
                window.socket.getLast().setName(socket.getName());
                window.sendStringMsg(socket.getName()+" entrou na sala...", window);
                while(socket.getSocket().isConnected()){
                    user = recvStringMsg();
                    msg = recvStringMsg();
                    if(user.equals("@logout")){
                        window.sendStringMsg(msg+" Saiu da sala... ", window);
                        window.deleteSocket(window, msg);
                        this.interrupt();
                    }
                    else{
                        if(!window.searchUserInList(user, window)){
                            window.sendStringMsg(socket.getName()+" disse para <TODOS>: "+msg, window);
                        }
                        else{
                            window.sendString(socket.getName()+" disse para <"+user+">: " +msg, window, user);
                        }
                    }
                }
            }
        }catch(Exception e){
            this.interrupt();
        }
    }
     
    public byte[] recvMsg() {
        byte[] msg = new byte[100];
        try {
            InputStream istream = socket.getSocket().getInputStream();
            istream.read(msg);
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
        return msg;
    }
    
    public String recvStringMsg() {
        try {
            InputStream istream = socket.getSocket().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(istream));
            return br.readLine();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
        return "";
    }
    
    public int recvInt() {
        try {
            InputStream istream = socket.getSocket().getInputStream();
            DataInputStream dis = new DataInputStream(istream);           
            return dis.readInt();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
        return 0;
    }
}
