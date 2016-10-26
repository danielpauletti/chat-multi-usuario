
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author daniel
 */
public class ChatServer {

    ServerSocket ss;
    Socket s;
    static List<ClienteConectado> usuariosConectados;
    Timer timer;

    public ChatServer() {
        try {
            ss = new ServerSocket(8088);
            usuariosConectados = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void recebeClientes() {
        ClienteConectado u;
        try {
            while (true) {
                System.out.println("AGUARDA CLIENTES");
                u = new ClienteConectado(ss.accept());
                u.start();
                usuariosConectados.add(u);              
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
