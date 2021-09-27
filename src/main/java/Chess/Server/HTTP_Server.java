package Chess.Server;

import Chess.Client.HTTP_ClientHandler;
import Chess.Game.Logic.Player.EPlayerColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

/**
 * @author Fitor Avdiji
 * <p>
 * Server, that communicates with the ChessClient
 */
public class HTTP_Server {


    /**
     * Port of this Server
     **/
    private static final int PORT = 80;

    /**
     * Random object
     **/
    private final static Random random = new Random();

    /**
     * Constructor
     **/
    public HTTP_Server() {
    }

    public void runServer() throws IOException, InterruptedException {

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Starting New Game");
                System.out.println("Waiting for Other Players...");

                EPlayerColor color1 = random.nextInt(2) == 0 ? EPlayerColor.WHITE : EPlayerColor.BLACK;
                EPlayerColor color2 = color1 == EPlayerColor.WHITE ? EPlayerColor.BLACK : EPlayerColor.WHITE;

                HTTP_ServerHandler player1 = new HTTP_ServerHandler(color1, serverSocket);
                HTTP_ServerHandler player2 = new HTTP_ServerHandler(color2, serverSocket);

                player1.handleGetRequest(color1.toString());
                player2.handleGetRequest(color2.toString());

                while (true) {
                    player2.establishConnection();
                    player1.establishConnection();
                    player2.handleGetRequest(HTTP_ClientHandler.SIGNAL_CONTINUE);
                    player1.handlePostRequest();
                    player2.establishConnection();
                    player2.handleGetRequest(player1.getLastMoveReceived());

                    System.out.println("WHITE Move: " + player1.getLastMoveReceived());
                    if (player1.getLastMoveReceived().equals(HTTP_ClientHandler.SIGNAL_FF_WHITE) ||
                            player1.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_WIN_WHITE) ||
                            player1.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_WIN_BLACK) ||
                            player1.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_STALEMATE)
                    ) {
                        break;
                    }

                    player1.establishConnection();
                    player2.establishConnection();
                    player1.handleGetRequest(HTTP_ClientHandler.SIGNAL_CONTINUE);
                    player2.handlePostRequest();
                    player1.establishConnection();
                    player1.handleGetRequest(player2.getLastMoveReceived());

                    System.out.println("BLACK Move: " + player2.getLastMoveReceived());
                    if (player2.getLastMoveReceived().equals(HTTP_ClientHandler.SIGNAL_FF_BLACK) ||
                            player2.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_WIN_WHITE) ||
                            player2.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_WIN_BLACK) ||
                            player2.getLastMoveReceived().equals(HTTP_ClientHandler.STRING_STALEMATE)
                    ) {
                        break;
                    }
                }
                System.out.println("Game Finished!");
            }
        }
    }
}
