package server;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ipLabel;
    private JTextField ipTextField;
    private JLabel portLabel;
    private JTextField portTextField;
    private JButton startButton;
    private JTextArea messageArea;
    private List<ClientHandler> clientHandlers;

    public Server() {
        // Configurar JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Painel superior
        JPanel topPanel = new JPanel(new FlowLayout());
        ipLabel = new JLabel("IP:");
        ipTextField = new JTextField(10);
        portLabel = new JLabel("Port:");
        portTextField = new JTextField(5);
        startButton = new JButton("Start");
        startButton.addActionListener(e -> startServer());
        topPanel.add(ipLabel);
        topPanel.add(ipTextField);
        topPanel.add(portLabel);
        topPanel.add(portTextField);
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        // Painel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        centerPanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Inicializar a lista de manipuladores de clientes
        clientHandlers = new ArrayList<>();
    }

    public void startServer() {
        try {
            String ip = ipTextField.getText();
            int port = Integer.parseInt(portTextField.getText());

            new Thread(() -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(ip));
                    ipTextField.setEnabled(false);
                    portTextField.setEnabled(false);
                    ipLabel.setText("IP: " + ip);
                    portLabel.setText("Port: " + port);
                    startButton.setEnabled(false);
                    messageArea.setText("Accepting Clients\n");

                    while (true) {
                        Socket socket = serverSocket.accept();
                        new Thread(() -> handleClient(socket)).start();
                    }
                } catch (BindException e) {
                    JOptionPane.showMessageDialog(this, "Port already in use", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UnknownHostException e) {
                    JOptionPane.showMessageDialog(this, "Unknown host", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid port number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleClient(Socket socket) {
        SwingUtilities.invokeLater(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String username = reader.readLine();
                System.out.println("A new client has connected: " + username);
                messageArea.append("A new client has connected: " + username + "\n");

                ClientHandler clientHandler = new ClientHandler(socket, username, this);
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        SwingUtilities.invokeLater(() -> {
            System.out.println("A client has disconnected: " + clientHandler.getUsername());
            messageArea.append("A client has disconnected: " + clientHandler.getUsername() + "\n");
        });
    }

    public void broadcastMessage(String message) {
        SwingUtilities.invokeLater(() -> {
          //  messageArea.append(message + "\n");
            for (ClientHandler handler : clientHandlers) {
                handler.sendMessage(message);
            }
        });
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Server server = new Server();
            server.setVisible(true);
            server.setLocationRelativeTo(null);
            server.setResizable(false);
        });
    }
}
