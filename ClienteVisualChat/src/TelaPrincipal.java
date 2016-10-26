
import java.util.Timer;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class TelaPrincipal extends javax.swing.JFrame{
    ClienteChat cliente;
    boolean first = true;
    String apelido = "";
    Timer timer;
    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        conectar();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                cliente.disconectaCliente();
                System.exit(0);
            }
        });
    }
    
    public void conectar(){
        cliente = new ClienteChat("localhost", 8088, this);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listApelidos = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMensagens = new javax.swing.JTextArea();
        btEnviar = new javax.swing.JButton();
        txtEntrada = new javax.swing.JTextField();
        chkPrivado = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente para Chat");

        jScrollPane1.setViewportView(listApelidos);

        txtMensagens.setColumns(20);
        txtMensagens.setRows(5);
        jScrollPane2.setViewportView(txtMensagens);

        btEnviar.setText("Enviar Mensagem");
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });

        chkPrivado.setText("Privado");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .add(chkPrivado, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(10, 10, 10)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(txtEntrada)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btEnviar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 277, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chkPrivado))
                    .add(jScrollPane2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btEnviar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(txtEntrada, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(26, 26, 26))
        );

        setSize(new java.awt.Dimension(736, 412));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEnviarActionPerformed
        // TODO add your handling code here:
        String msg;
        String para = "";
        if(first){
            msg = txtEntrada.getText();
            apelido = txtEntrada.getText();
            this.setTitle(msg);
            first = false;
            cliente.enviaMensagem(apelido);
            cliente.enviaMensagem("/lista");
        }else{
            msg = txtEntrada.getText();
            
            if(chkPrivado.isSelected()){
                para = listApelidos.getSelectedValue().toString().trim();
                msg = "/privado " + para + " " + msg;
            }else{
                msg = "/mensagem " + msg;
            }
            
            cliente.enviaMensagem(msg);
        }
        txtEntrada.setText("");
    }//GEN-LAST:event_btEnviarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEnviar;
    public javax.swing.JCheckBox chkPrivado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JList listApelidos;
    private javax.swing.JTextField txtEntrada;
    public javax.swing.JTextArea txtMensagens;
    // End of variables declaration//GEN-END:variables

    public void mostraListaUsuarios(final String[] listaUsuarios) {
        ListModel<String> model = new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return listaUsuarios.length;
            }

            @Override
            public String getElementAt(int index) {
                return listaUsuarios[index];
            }
        };
        listApelidos.setModel(model);
    }
}
