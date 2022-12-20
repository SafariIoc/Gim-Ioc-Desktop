/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import modelo.SQLClasse;

/**
 *
 * @author Sigriid
 */
public class LlistaClasse extends javax.swing.JFrame {
    
    private final SQLClasse SQLC;

    /**
     * Creates new form LlistaClasse
     */
    public LlistaClasse() {
        initComponents();
        SQLC =new SQLClasse();
        Llistar();        
        this.setLocationRelativeTo(null);
    }
    
    //creem mètode per llistar
    private void Llistar(){
        taulaClasses.setModel(SQLC.GetDades());
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        titol = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SelecClass = new javax.swing.JComboBox<>();
        btFiltrar = new javax.swing.JButton();
        TipusVariable = new javax.swing.JComboBox<>();
        textFiltr = new javax.swing.JTextField();
        btTornar = new javax.swing.JButton();
        botEliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taulaClasses = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/logo ioc mini.png"))); // NOI18N

        titol.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titol.setText("Llistat de classes GIM-IOC");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Seleccionar el tipus de classe");

        SelecClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Virtual", "Aula" }));

        btFiltrar.setBackground(new java.awt.Color(153, 153, 153));
        btFiltrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btFiltrar.setText("Llistar classes filtrades");
        btFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltrarActionPerformed(evt);
            }
        });

        TipusVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tipus de classe" }));

        textFiltr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFiltrKeyReleased(evt);
            }
        });

        btTornar.setBackground(new java.awt.Color(153, 153, 153));
        btTornar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btTornar.setText("Tornar");
        btTornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTornarActionPerformed(evt);
            }
        });

        botEliminar.setBackground(new java.awt.Color(153, 153, 153));
        botEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botEliminar.setText("Esborrar classe");
        botEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botEliminarActionPerformed(evt);
            }
        });

        taulaClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(taulaClasses);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titol)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(TipusVariable, javax.swing.GroupLayout.Alignment.LEADING, 0, 97, Short.MAX_VALUE)
                                    .addComponent(SelecClass, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textFiltr)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btTornar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titol)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SelecClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btFiltrar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TipusVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFiltr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(btTornar)
                .addGap(18, 18, 18)
                .addComponent(botEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btTornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTornarActionPerformed
        //Funció del botó tornar, per retornar a la pantalla anterior sense tancar el programa:
        this.setVisible(false);
        PanelUser panelu = new PanelUser();
        panelu.setVisible(true);    
    }//GEN-LAST:event_btTornarActionPerformed

    private void btFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltrarActionPerformed
        //filtrar classes per selecció de tipus
        taulaClasses.setModel(SQLC.GetDada(SelecClass.getSelectedIndex()));
    }//GEN-LAST:event_btFiltrarActionPerformed

    private void botEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botEliminarActionPerformed
        //acció al clicar al botó esborrar per esborrar la classe seleccionada
        //creem variable
        int fila =taulaClasses.getSelectedRowCount();
        
        //verifiquem si ha alguna fila seleccionada o no
        if (fila < 1){
            //Mostrem missatge d'avís conforme cal seleccionar una fila
            JOptionPane.showMessageDialog(null, "Selecciona un registre de la taula");
                    
        }else{
            //Si ja està seleccionat cridem el mètode per esborrar classes
            SQLC.deleteClasse(taulaClasses.getValueAt(taulaClasses.getSelectedRow(), 0).toString());            
            
        }
    }//GEN-LAST:event_botEliminarActionPerformed

    private void textFiltrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFiltrKeyReleased
        //esdeveniment al pitjar la tecla enter
        char tecla = evt.getKeyChar();
        //verifiquem si la tecla pitjada és enter
        if (tecla == KeyEvent.VK_ENTER){
            taulaClasses.setModel(SQLC.GetDadaFiltre(TipusVariable.getSelectedIndex(),textFiltr.getText()));
        }
    }//GEN-LAST:event_textFiltrKeyReleased

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
            java.util.logging.Logger.getLogger(LlistaClasse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LlistaClasse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LlistaClasse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LlistaClasse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LlistaClasse().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SelecClass;
    private javax.swing.JComboBox<String> TipusVariable;
    private javax.swing.JButton botEliminar;
    private javax.swing.JButton btFiltrar;
    private javax.swing.JButton btTornar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable taulaClasses;
    private javax.swing.JTextField textFiltr;
    private javax.swing.JLabel titol;
    // End of variables declaration//GEN-END:variables
}
