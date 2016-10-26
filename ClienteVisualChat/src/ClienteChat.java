
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class ClienteChat implements Runnable {
    Socket s;
    PrintWriter out;
    BufferedReader in;
    Thread th;
    String host;
    int port;
    TelaPrincipal tela;

    public ClienteChat(String host, int port, TelaPrincipal screen) {
        this.host = host;
        this.port = port;
        this.tela = screen;
        setupClient();
    }
    
    public void disconectaCliente(){
        enviaMensagem("/sair");
    }

    public void enviaMensagem(String msg) {
        try {
            out.println(msg);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void recebeMensagem() {
        try {
            String m = in.readLine();
            System.out.println("RECEIVE");
            if(m.indexOf("lista:")>=0){
                String msg = m.substring(m.indexOf("lista:")+6, m.length());
                msg = msg.replaceAll("\\[", "").replaceAll("\\]", "").trim();
                String [] lista = msg.split(",");
                
                tela.mostraListaUsuarios(lista);
                System.out.println("sdsd");
            }else{
                tela.txtMensagens.append(m+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            recebeMensagem();
        }
    }

    private void setupClient() {
        try {
            s = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
            th = new Thread(this);
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
