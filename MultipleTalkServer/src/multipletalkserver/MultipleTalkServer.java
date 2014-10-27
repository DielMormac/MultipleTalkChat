package multipletalkserver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;


/*  Centro Universitário Senac
    Campus: Santo Amaro
    Curso: Tecnologia em Jogos Digitais
    Disciplina: Jogos em Rede
    Alunos:
            Marcos Antônio Marcon(Diel Mormac)
            Ricardo Gaede Nogueira
*/
public class MultipleTalkServer extends JFrame implements ActionListener{

    public static ServerSocket mainSocket;
    public LinkedList<SocketsTCP> socket;
    private static SimpleThread t;
    private JMenuBar menuBar = new JMenuBar();
    private static JMenu menuCredits;
    private BorderLayout layout;
    private static boolean isFull = false;
    
    public MultipleTalkServer(){
        createMainSocket();
        socket = new LinkedList<SocketsTCP>();
        layout = new BorderLayout(5, 5); 
        getContentPane().setLayout(layout);

        menuCredits = new JMenu("Creditos");
        menuBar.add(menuCredits);
        menuCredits.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuAboutMouseClicked(evt);
            }
        });
        
        add(menuBar, BorderLayout.NORTH);
    }
    
    public static void main(String[] args) {
        MultipleTalkServer server = new MultipleTalkServer();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.setSize(300, 300);
        server.setTitle("Multiple Talk Server");
        server.setVisible(true);
        while(!server.mainSocket.isClosed()){
           chargeSocket(server);
        }
    }
    
    private void createMainSocket(){
        try{
            mainSocket = new ServerSocket(4545);
        }catch(Exception err){
            JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
            System.exit(0);
        }        
    }
    public static void chargeSocket(MultipleTalkServer server){
        if(!isFull){
            SocketsTCP s = new SocketsTCP();
            server.socket.add(s);
            try{
                server.socket.getLast().setSocket(server.mainSocket.accept());
            }catch (Exception err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
            }
            t = new SimpleThread(server.socket.getLast(), server);
            t.start();
            if(server.socket.size() >= 15){
                isFull = true;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "O servidor atingiu o limite de usuários", "Alerta!", JOptionPane.PLAIN_MESSAGE , null);
            if(server.socket.size() < 15){
                isFull = false;
            }
        }
    }
    public void deleteSocket(MultipleTalkServer server, String ip){
        try {
            for (int i=0; i < server.socket.size()-1; i++){
                if(ip.equals(server.socket.get(i).getName())){
                    server.socket.get(i).close();
                    server.socket.remove(i);
                }
            }
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "erro remover usuário da lista", null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    public boolean searchUserInList(String name, MultipleTalkServer server){
        try {
            for (int i=0; i < server.socket.size()-1; i++){
                if(name.equals(server.socket.get(i).getName())){
                    return true;
                }
            }
            return false;
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "Erro ao varrer lista", null, JOptionPane.PLAIN_MESSAGE , null);
             return false;
        }
    }
    public void sendMsg(byte[] msg, MultipleTalkServer server) {
        try {
            for (int i=0; i < server.socket.size()-1; i++){
                OutputStream ostream = server.socket.get(i).getSocket().getOutputStream();
                ostream.write(msg);
            }
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "erro ao enviar msg", null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    public void sendStringMsg(String msg, MultipleTalkServer server) {
    	try {
            for (int i=0; i < server.socket.size()-1; i++){
                OutputStream ostream = server.socket.get(i).getSocket().getOutputStream();
                PrintWriter pw = new PrintWriter(ostream, true);
                pw.println(msg);
            }
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "Erro ao enviar String", null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    
    public void sendString(String msg, MultipleTalkServer server, String str) {
    	try {
            for (int i=0; i < server.socket.size()-1; i++){
                if(str.equals(server.socket.get(i).getName())){
                    OutputStream ostream = server.socket.get(i).getSocket().getOutputStream();
                    PrintWriter pw = new PrintWriter(ostream, true);
                    pw.println(msg);
                }
            }
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "Erro ao enviar String", null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    
    public void sendInt(int val, MultipleTalkServer server) {
    	try {
            for (int i=0; i < server.socket.size()-1; i++){
                OutputStream ostream = server.socket.get(i).getSocket().getOutputStream();
                DataOutputStream dos = new DataOutputStream(ostream);
                dos.writeInt(val);
            }
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, "Erro ao enviar INT", null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    private void menuAboutMouseClicked(MouseEvent evt) {
        JOptionPane.showMessageDialog(null, "Centro Universitário Senac\n"
                                            + "Campus: Santo Amaro\n"
                                            + "Curso: Tecnologia em Jogos Digitais\n"
                                            + "Disciplina: Jogos em Rede\n"
                                            + "Alunos:\n"
                                            + "Marcos Antônio Marcon(Diel Mormac)\n"
                                            + "Ricardo Gaede Nogueira\n",
                                            "Creditos", JOptionPane.PLAIN_MESSAGE , null);         
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
    }
    
    public boolean IsFull(){
        return isFull;
    }
    
}
