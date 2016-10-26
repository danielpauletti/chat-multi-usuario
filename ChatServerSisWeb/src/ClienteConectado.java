
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class ClienteConectado implements Runnable {

    String apelido;
    Socket s;
    PrintWriter out;
    BufferedReader in;
    Thread th;
    boolean connected = true;

    public ClienteConectado(Socket s) {
        this.s = s;
        setUp();
    }

    public void start(){
        th.start();
    }
    
    void setUp() {
        try {            
            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            enviaMensagem("Informe o apelido");
            th = new Thread(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void enviaMensagem(String msg) {
        try {
            out.println(msg);
            out.flush();
            System.out.println("Enviou: " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void recebeMensagem() {
        String msg;
        try {
            msg = in.readLine();
            System.out.println("Recebeu: " + msg);
            
            if(apelido == null){
                if(apelidoValido(msg)){
                    apelido = msg;
                    System.out.println("Ganhou apelido ");
                }else{
                    String m = "Apelido já está sendo usado, escolha outro ";
                    System.out.println(m);
                    enviaMensagem(m);
                }                
            }
            
            if(msg.indexOf("/mensagem") > -1){
                String mensagem = msg.substring(10, msg.length());
                enviaMensagemPublica(mensagem);
            }
            
            if(msg.indexOf("/privado") > -1){
                String msgSemComando = msg.substring(9, msg.length());
                String para = msgSemComando.substring(0, msgSemComando.indexOf(" "));
                int indexMsg = msgSemComando.indexOf(para)+para.length();
                String mensagem = msgSemComando.substring(indexMsg, msgSemComando.length());
                enviaMensagemPrivada(mensagem, para);
            }
            
            if(msg.indexOf("/lista") > -1){
                enviaListaUsuarios();
            }
            
            if(msg.indexOf("/sair") > -1){
                disconectaCliente();
            }
        } catch (Exception e) {
            connected = false;
            e.printStackTrace();
        }
    }
    
    private void disconectaCliente(){
        try{
            enviaMensagemPublica("Saiu");
            ChatServer.usuariosConectados.remove(this);
            enviaListaUsuarios();
            s.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {    
        recebeMensagem();
        while (connected) {
            recebeMensagem();
        }
    }
    
    private Boolean apelidoValido(String apelido){
        for (ClienteConectado usuario : ChatServer.usuariosConectados) {
            if (usuario.apelido != null && usuario.apelido.equals(apelido)) {
                return false;
            }
        }
        
        return true;
    }

    public void enviaMensagemPublica(String msg) {
        System.out.println("ENVIA MSG PUBLICA");
        for (ClienteConectado usuario : ChatServer.usuariosConectados) {
            usuario.enviaMensagem(this.apelido+": "+msg);
        }
    }

    public void enviaMensagemPrivada(String msg, String para) {
        for (ClienteConectado usuario : ChatServer.usuariosConectados) {
            if (usuario.apelido.equals(para)) {
                usuario.enviaMensagem("(Privado) "+this.apelido +": " + msg);
                this.enviaMensagem("Privado para " + usuario.apelido + ": " + msg);
            }
        }
    }

    public void enviaListaUsuarios() {
        List<String> usuarios = new ArrayList<String>();
        for (int i = 0; i < ChatServer.usuariosConectados.size(); i++) {
            usuarios.add(ChatServer.usuariosConectados.get(i).apelido);
        }
        
        for (ClienteConectado usuario : ChatServer.usuariosConectados) {
            usuario.enviaMensagem("lista:"+usuarios);
        }
    }
}
