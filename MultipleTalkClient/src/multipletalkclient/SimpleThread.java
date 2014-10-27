package multipletalkclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class SimpleThread extends Thread{

    private SocketsTCP socket;
    MultipleTalkClient window;
      
    public SimpleThread(SocketsTCP skt, MultipleTalkClient client){
        super();
        socket = skt;
        window = client;
    }
    @Override
    public void run(){
        try{
            while(socket.getSocket().isConnected()){
                window.addStrTextArea(recvStringMsg());
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
