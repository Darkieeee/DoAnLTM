package connection;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * @author Loan (^._.^)ﾉ
 */
public class ServerTCP implements Runnable {
    private Socket socket = null;
    private String myName = "";
    private BufferedReader in;
    private BufferedWriter out;
    private SecretKey sessionKey;
    private String strSessionKey;
    private String original;
    public ServerTCP(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.myName = name;
    }
    @Override
    public void run() {
        System.out.println("Client "+socket.toString()+"accepted");
        String input = "";
        try {
            for(ServerTCP server: ServerExecute.servers){
                if(myName.equals(server.myName)){
                    String strPublicKey = Encryption.getStringPublicKey();
                    System.out.println("Public key: "+strPublicKey);
                    server.out.write(strPublicKey+'\n');
                    server.out.flush();
                    System.out.println("Server write: " + strPublicKey + " to " + server.myName);
                    input = in.readLine();
                    System.out.println("Server received session key: " + input + " from " + socket.toString() + " # Client " + myName);
                    strSessionKey = Encryption.decryptSessionKey(input);
                    sessionKey = Encryption.getSecretKeyFromString(strSessionKey);
                    System.out.println("Session key: "+strSessionKey);
                    break;
                }
            }

            while (true) {
                input = in.readLine();
                input = Encryption.decryptData(sessionKey, input);
                System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + myName);
                for(ServerTCP server: ServerExecute.servers){
                    if(myName.equals(server.myName)){
                        String response = input.toUpperCase(); //response upper string input
                        response = Encryption.encryptData(sessionKey, response); //encrypt data before send
                        server.out.write(response+'\n');
                        server.out.flush();
                        System.out.println("Server write: " + response + " to " + server.myName);
                        break;
                    }
                }
            }
        } catch (IOException | NullPointerException e){
            System.out.println("Client "+myName+" exit");
            ServerExecute.servers.remove(this);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
