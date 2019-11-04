/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pict.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import library.server.User;

/**
 *
 * @author DELL_3542
 */
public class LibrarianLogin extends javax.swing.JFrame {

    /**
     * Creates new form LibrarianLogin
     */
    User lu;
    public LibrarianLogin(User lu) {
        initComponents();
        this.lu=lu;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        Submit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel1.setText("LIBRARIAN LOGIN");

        jLabel2.setFont(new java.awt.Font("Vani", 3, 14)); // NOI18N
        jLabel2.setText("USERNAME");

        jLabel3.setFont(new java.awt.Font("Vani", 3, 14)); // NOI18N
        jLabel3.setText("PASSWORD");

        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        Submit.setText("SUBMIT");
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Submit)
                .addGap(164, 164, 164))
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(username)
                    .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Submit)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        // TODO add your handling code here:
        
        try
        {  
            String user1=username.getText();
            String pass1=String.valueOf(password.getPassword());
            int flag=lu.adminLogin(user1, pass1);
            if(flag==1)
            {
                 Options o1=new Options(lu);
                 o1.setVisible(true);
                 this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "INCORRECT USERNAME AND PASSWORD", "ERROR", HEIGHT);
            }
            
        }
        catch(Exception e)
        {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Try Again", "ERROR", HEIGHT);
        }
        
        
        
       
    }//GEN-LAST:event_SubmitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}