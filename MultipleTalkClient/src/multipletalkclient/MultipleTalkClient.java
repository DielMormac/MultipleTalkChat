package multipletalkclient;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*  Centro Universitário Senac
    Campus: Santo Amaro
    Curso: Tecnologia em Jogos Digitais
    Disciplina: Jogos em Rede
    Alunos:
            Marcos Antônio Marcon(Diel Mormac)
            Ricardo Gaede Nogueira
*/
public class MultipleTalkClient extends javax.swing.JFrame implements ActionListener{
    
    public SocketsTCP socket;
    private static SimpleThread t;
    private JMenuBar menuBar;
    private JMenu menuCredits;
    private JButton buttonSend, buttonDisconect, buttonFile;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JTextField jTextField1, JTA;
    private BorderLayout layoutMain;
    private JPanel panelSouth, panelEast;
    private JFileChooser fc;
    
    public MultipleTalkClient(){
        chargeSocket();
        chargeInterface();
    }
    public static void main(String[] args) {
        MultipleTalkClient client = new MultipleTalkClient();
        client.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        client.setSize(680, 420);
        client.setVisible(true);
        t = new SimpleThread(client.socket, client);
        t.start();
    }
    private void chargeInterface(){
        layoutMain = new BorderLayout(10,10);
        getContentPane().setLayout(layoutMain);
        panelSouth = new JPanel();
        panelSouth.setLayout(new BorderLayout(5,5));
        panelEast = new JPanel();
        panelEast.setLayout(new BorderLayout(5,5));
        
        jTextField1 = new JTextField();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        JTA = new JTextField();
        buttonSend = new JButton();
        buttonDisconect = new JButton();
        buttonFile = new JButton();
        menuBar = new JMenuBar();
        menuCredits = new JMenu("Creditos");
        fc = new JFileChooser();

        menuCredits.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuAboutMouseClicked(evt);
            }
        });
        
        menuBar.add(menuCredits);
        
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
        
        JTA.setColumns(10);
        JTA.setText("Todos");
        
        buttonSend.setText("Enviar");
        buttonSend.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        
        buttonDisconect.setText("Desconectar");
        buttonDisconect.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        
        buttonFile.setText("Arquivo");
        buttonFile.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        
        setTitle(socket.getName()+" - Multiple Talk Client");
        add(menuBar, BorderLayout.NORTH);
        add(jScrollPane1, BorderLayout.CENTER);
        add(panelEast, BorderLayout.EAST);
        panelEast.add(buttonSend, BorderLayout.CENTER);
        panelEast.add(buttonDisconect, BorderLayout.NORTH);
        panelEast.add(buttonFile, BorderLayout.SOUTH);
        add(panelSouth, BorderLayout.SOUTH);
        panelSouth.add(JTA, BorderLayout.WEST);
        panelSouth.add(jTextField1, BorderLayout.CENTER);
	
    }
    private void chargeSocket(){
        socket = new SocketsTCP();
        sendStringMsg(socket.getName().trim());     
        if(socket.getSocket().isConnected()){
            JOptionPane.showMessageDialog(null, "Bem-vindo ao Chat!", "Bem-Vindo!", JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    
    public void sendMsg(byte[] msg) {
        try {
                OutputStream ostream = socket.getSocket().getOutputStream();
                ostream.write(msg);
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }

    public void sendStringMsg(String msg) {
    	try {
                OutputStream ostream = socket.getSocket().getOutputStream();
                PrintWriter pw = new PrintWriter(ostream, true);
                pw.println(msg);
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    
    public void sendInt(int val) {
    	try {
                OutputStream ostream = socket.getSocket().getOutputStream();
                DataOutputStream dos = new DataOutputStream(ostream);
                dos.writeInt(val);
        } catch (Exception err) {
             JOptionPane.showMessageDialog(null, err.getMessage(), null, JOptionPane.PLAIN_MESSAGE , null);
        }
    }
    
    public void addStrTextArea(String str) {
        jTextArea1.append(str.trim() + "\n");
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
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        if(!jTextField1.getText().isEmpty()){
            sendStringMsg(JTA.getText());
            sendStringMsg(jTextField1.getText());
            JTA.setText("Todos");
            jTextField1.setText(null);
        }
    }
    
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
            sendStringMsg("@logout");
            sendStringMsg(socket.getName());
    }
    
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {
            sendStringMsg("@File");
            sendStringMsg(JTA.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
