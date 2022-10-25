/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.mysql.jdbc.Statement;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Sigriid
 */
public class SQLUsuaris extends connexio{
    public boolean registrar (usuaris usr){
        PreparedStatement ps = null;
        Connection con = getConnexio();
        
        String sql = "INSERT INTO usuaris (usuari, password, nom, correu, id_tipus) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuari());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNom());
            ps.setString(4, usr.getCorreu());
            ps.setInt(5, usr.getId_tipus());
            ps.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLUsuaris.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //llistar usuaris
    
    
    //comprobació usuari per login
    public boolean login (usuaris usr){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnexio();
        
        //consulta sql
        String sql = "SELECT  id, password, nom, correu, id_tipus FROM usuaris WHERE usuari=?";
        
        try{
            ps =con.prepareStatement(sql);
            ps.setString(1,usr.getUsuari());
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                if (usr.getPassword().equals(rs.getString(2))){
                    
                    String sqlUpdate = "UPDATE usuaris SET last_session =? where id =?";
                    
                    ps = con.prepareStatement (sqlUpdate);
                    ps.setString(1, usr.getLast_session());
                    ps.setInt(2,rs.getInt(1));
                    ps.execute();
                    
                    usr.setId(rs.getInt(1));
                    usr.setNom(rs.getString(3)); 
                    usr.setCorreu(rs.getString(4));
                    usr.setId_tipus(rs.getInt(5));
                    return true;
                    
                }else{
                    return false;
                }
                
            }
            
            return false;
            
        }catch (SQLException ex){
            Logger.getLogger(SQLUsuaris.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }

   
    
    
}

