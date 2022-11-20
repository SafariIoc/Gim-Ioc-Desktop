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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sigriid
 */
public class SQLUsuaris extends connexio{
    
    //creem consulta per llistar usuaris
    private final String SQL_SELECT= "SELECT * FROM usuaris";
    //Creem objecte privat
    private DefaultTableModel DT;
    private ResultSet RS;
    PreparedStatement PS;
    
    //creem mètode per obtenir els títols de la taula
    private DefaultTableModel setTitols(){
        
        //afegir el títols que volem visualitzar a la taula
        DT = new DefaultTableModel();
        DT.addColumn("Id");
        DT.addColumn("Usuari");
        DT.addColumn("Nom");
        DT.addColumn("Contrasenya");
        DT.addColumn("Correu");        
        DT.addColumn("Id Tipus");
        
        return DT;
    }
    
    //mètode per registrar usuaris
    public boolean Registrar (usuaris usr){
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
    
    
    //mètode per confirmar si existeix un usuari a la BD
    public int ExisteixUsuari (String usuari){
        PreparedStatement ps = null;
        ResultSet rs= null;
        Connection con = getConnexio();
        
        String sql = "SELECT count(id) FROM usuaris WHERE usuari = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuari);            
            rs = ps.executeQuery();
            
            //verifiquem si retorna dades
            if(rs.next()){
                return rs.getInt(1);
            }
            
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLUsuaris.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
    
    //mètode per verificar que el valor del camp de correu al registre tingui format de correu electrònic
    public boolean EsEmail (String correu){
        //patró per validar email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher mather = pattern.matcher(correu);
        return mather.find();
    }
    
    
    //creem mètode per obtenir les dades de la BD de MYSQLWorkbench
    public DefaultTableModel GetDades(){
        try{
            //obtenim títols de la taula
            setTitols();
            PS = null;
            Connection con = getConnexio();
            PS = con.prepareStatement(SQL_SELECT);
            RS = PS.executeQuery();
            
            //creem array per guardar les dades de la consulta
            Object[] fila = new Object[6];
            
            //Obtenim les dades de les files conforme la informació de la nostre BD
            while (RS.next()){
                fila[0]= RS.getInt(1);
                fila[1]= RS.getString(2);
                fila[2]= RS.getString(3);
                fila[3]= RS.getString(4);
                fila[4]= RS.getString(5);               
                fila[5]= RS.getInt(6);
                DT.addRow(fila); //afegim valor a la fila
                
            }
        }catch (SQLException e ){
            System.out.println("Error al llistar dades:" +e.getMessage());
        }finally{
            PS = null;
            RS = null;            
            
        }
        
        return DT;
    }
    
    //creem mètode per filtrar tipus usuari a l'hora d'obtenir les dades BD
    public DefaultTableModel GetDada(int id_tipus){
        String SQL = null;
        
        //creem un switch per cada opció de filtrar
        switch (id_tipus) {
            case 1:
                //tipus usuari 
                SQL = "SELECT * FROM usuaris WHERE id_tipus = 2";
                break;
            case 2:
                SQL = "SELECT * FROM usuaris WHERE id_tipus = 3";
                break;            
            default:
                SQL = "SELECT * FROM usuaris WHERE id_tipus = 1";
                break;
        }
        
        try{
            //obtenim títols de la taula
            setTitols();
            PS = null;
            Connection con = getConnexio();
            PS = con.prepareStatement(SQL);
            RS = PS.executeQuery();
            
            //creem array per guardar les dades de la consulta
            Object[] fila = new Object[6];
            
            //Obtenim les dades de les files conforme la informació de la nostre BD
            while (RS.next()){
                fila[0]= RS.getInt(1);
                fila[1]= RS.getString(2);
                fila[2]= RS.getString(3);
                fila[3]= RS.getString(4);
                fila[4]= RS.getString(5);
                fila[5]= RS.getInt(6);                
                DT.addRow(fila); //afegim valor a la fila
                
            }
        }catch (SQLException e ){
            System.out.println("Error al llistar dades:" +e.getMessage());
        }finally{
            PS = null;
            RS = null;            
            
        }
        
        return DT;
    }
    
    
    
    //comprobació usuari per login
    public boolean Login (usuaris usr){
        
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
    
    //mètode per eliminació d'usuaris 
    public int deleteUser(String id){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnexio();
        int res = 0;
        
        //consultar per eliminar usuari per id
        String SQL = "DELETE from usuaris WHERE id=" +id;
        try{
            ps = con.prepareStatement(SQL);
            res =ps.executeUpdate();
            if(res>0){
                JOptionPane.showMessageDialog(null,"Usuari esborrat");              
            }
            //excepció en cas d'error en l'eliminació
        }catch (SQLException e){
            System.err.println("Error al esborrar l'usuari" + e.getMessage());
        }finally{
            ps= null;            
        }
        return res;
    }
    
    
    
  
    
}

