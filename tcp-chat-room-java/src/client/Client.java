package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            
            // Enviar o nome de usuário para o servidor
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(JTextArea messageArea) {
        new Thread(() -> {
            try {
                String msgFromGroupChat;
                while ((msgFromGroupChat = bufferedReader.readLine()) != null) {
                    final String finalMsgFromGroupChat = msgFromGroupChat;
                    SwingUtilities.invokeLater(() -> messageArea.append(finalMsgFromGroupChat + "\n"));
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = JOptionPane.showInputDialog(null, "Enter your username for the group chat:");
            
            if (username == null || username.isEmpty()) {
                System.exit(0);
            }

            try {
                String ip = JOptionPane.showInputDialog(null, "Enter the IP Adress to connect in the group chat:");
                String port =  JOptionPane.showInputDialog(null, "Enter the Port to connect in the group chat:");
  
                int portNumber = Integer.parseInt(port);
                Socket socket = new Socket(ip, portNumber);
                
                
                
                //Socket socket = new Socket("localhost", 9999);
                Client client = new Client(socket, username);

                ClientFrame clientFrame = new ClientFrame(client);
                clientFrame.setTitle("Chat Client");
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientFrame.setSize(400, 300);
                clientFrame.setVisible(true);
                clientFrame.setResizable(false);

                client.listenForMessage(clientFrame.getMessageArea());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid port. Please insert a number format.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
    }
}

class ClientFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea messageArea;
    private JTextField messageField;
    private Client client;

    public ClientFrame(Client client) {
        this.client = client;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    client.sendMessage(message);
                    messageField.setText("");
                }
            }
        });
        add(messageField, BorderLayout.SOUTH);
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }
}
