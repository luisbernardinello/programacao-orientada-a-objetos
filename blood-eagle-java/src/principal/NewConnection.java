package principal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entidades.Player;
import entidades.Player2;
import estadosdojogo.EstadoJogo;
import estadosdojogo.Jogando;
import principal.Game;
//classe para comunicar com o servidor.
public class NewConnection {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Player player;
    private Player2 player2;
    private Jogando playing;
    private Game game;

    public NewConnection(Player player, Player2 player2, Jogando playing) {
        this.playing = playing;
        this.player = player;
        this.player2 = player2;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Conectado!");

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            boolean isPlayer1 = input.readBoolean();

            if (isPlayer1) {
                System.out.println("Player 1!");

                output.writeObject(player);
                output.flush();

                player = (Player) input.readObject();

                boolean isPlayer2Connected = false;

                isPlayer2Connected = input.readBoolean();
                playing.setIsP2InGame(isPlayer2Connected);

             
                if (isPlayer2Connected) {
                	EstadoJogo.estado = EstadoJogo.JOGANDO;
                    player2 = (Player2) input.readObject();
                }

                
            } else {
                System.out.println("PLayer 2!");

                player = (Player) input.readObject();

          
                output.writeObject(player2);
                output.flush();

             
                player2 = (Player2) input.readObject();
            }

         
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
