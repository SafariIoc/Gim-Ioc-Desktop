/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author Sigriid
 */
public class connexio {
    private final String base = "usuaris?characterEncoding=latin1";
    private final String user = "root";
    private final String password = "root";
    private final String url = "jdbc:mysql://localhost:3306/" + base;
    private Connection con = null;
    public Statement sentencia;
    public ResultSet resultat;
    
    public Connection getConnexio(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
            sentencia = (Statement) con.createStatement();
        } catch (ClassNotFoundException  | SQLException ex) {
            Logger.getLogger(connexio.class.getName()).log(Level.SEVERE, null, ex);
        
        }    
        return con;
    }
}
