package connection;
import classes.connectDatabase.BLL.HocPhanBLL;
import classes.model.HocPhan;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import org.json.JSONObject;
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
    private final HocPhanBLL hocphanBLL = new HocPhanBLL(); // truy van CSDL hoc phan
    public ServerTCP(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.myName = name;
    }
    private String doCommand(String input){
        String[] parseInput = input.split(";");
        String result = null;
        if(parseInput.length == 2)
        {
            switch(parseInput[0]){
                case "REGISTERCOURSE":{
                    HocPhan hocphan = hocphanBLL.getMaHocPhan(parseInput[1]);
                    if(hocphan != null)
                    {
                        JSONObject resData = new JSONObject();
                        resData.put("status", true);
                        LinkedHashMap<String,String> hpData = new LinkedHashMap<>();
                        hpData.put("mamh",hocphan.getMaMonHoc());
                        hpData.put("tenmh",hocphan.getTenMonHoc());
                        hpData.put("nhommh",hocphan.getNhomMonHoc());
                        hpData.put("makhoa",hocphan.getMaKhoa());
                        resData.put("hocphan", hpData);
                        System.out.println(resData.toString());
                        result = resData.toString();
                    }
                    else {
                        JSONObject resData = new JSONObject();
                        resData.put("status", false);
                        resData.put("message", "Không tìm thấy mã học phần trong học kì này");
                        result = resData.toString();
                    }
                }
                break;
            }
        }
        return result;
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
                        String response = doCommand(input);
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
