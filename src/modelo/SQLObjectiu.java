/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sigriid
 */
public class SQLObjectiu extends Connexio{
    //creem consulta per llistar usuaris
    private final String SQL_SELECT= "SELECT * FROM objectius";
    
    //Creem objecte privat
    private DefaultTableModel DT;
    private ResultSet RS;
    PreparedStatement PS;
    
    //creem mètode per obtenir els títols de la taula
    private DefaultTableModel setTitols(){
        
        //afegir el títols que volem visualitzar a la taula
        DT = new DefaultTableModel();
        DT.addColumn("Id");
        DT.addColumn("Nom");               
        DT.addColumn("Id Tipus");
        
        return DT;
    }
    //mètode per registrar objectius
    public boolean Registrar (Objectiu obj){
        PreparedStatement ps = null;
        Connection con = getConnexio();
        
        String sql = "INSERT INTO objectius ( nom, id_tipus) VALUES (?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getNom());            
            ps.setInt(2, obj.getId_tipus());
            ps.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLObjectiu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //mètode per confirmar si existeix un objectiu a la BD
    public int ExisteixObjectiu (String usuari){
        PreparedStatement ps = null;
        ResultSet rs= null;
        Connection con = getConnexio();
        
        String sql = "SELECT count(id) FROM objectiu WHERE objectiu = ?";
        
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
            Logger.getLogger(SQLObjectiu.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
    
    //creem mètode per obtenir les dades de la BD 
    public DefaultTableModel GetDades(){
        try{
            //obtenim títols de la taula
            setTitols();
            PS = null;
            Connection con = getConnexio();
            PS = con.prepareStatement(SQL_SELECT);
            RS = PS.executeQuery();
            
            //creem array per guardar les dades de la consulta
            Object[] fila = new Object[3];
            
            //Obtenim les dades de les files conforme la informació de la nostre BD
            while (RS.next()){
                fila[0]= RS.getInt(1);
                fila[1]= RS.getString(2);                               
                fila[2]= RS.getInt(3);
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
    
    //creem mètode per filtrar tipus objectiu de la BD
    public DefaultTableModel GetDada(int id_tipus){
        String SQL = null;
        
        //creem un switch per cada opció de filtrar
        switch (id_tipus) {
            case 1:
                //tipus usuari 
                SQL = "SELECT * FROM objectius WHERE id_tipus = 2";
                break;
            case 2:
                SQL = "SELECT * FROM objectius WHERE id_tipus = 3";
                break;            
            default:
                SQL = "SELECT * FROM objectius WHERE id_tipus = 1";
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
            Object[] fila = new Object[3];
            
            //Obtenim les dades de les files conforme la informació de la nostre BD
            while (RS.next()){
                fila[0]= RS.getInt(1);
                fila[1]= RS.getString(2);                
                fila[2]= RS.getInt(3);                
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
    
    //creem mètode per obtenir les dades de la BD de MYSQLWorkbench amb filtre
    public DefaultTableModel GetDadaFiltre(int criteri, String parametre){
        String SQL;
        
        //si seleccionem la primera opció ID escribint al quadre i intro
        if(criteri == 0){
            SQL = "SELECT * FROM objectius WHERE idObjectius = " +parametre;
            
        //si escollim filtrar per nom escrit al quadre de text i intro
        }else{
            SQL ="SELECT * FROM objectius WHERE nom like '" +parametre+ "%'";
            
        }
            
        try{
            //obtenim títols de la taula
            setTitols();
            PS = null;
            Connection con = getConnexio();
            PS = con.prepareStatement(SQL);
            RS = PS.executeQuery();
            
            //creem array per guardar les dades de la consulta
            Object[] fila = new Object[3];
            
            //Obtenim les dades de les files conforme la informació de la nostre BD
            while (RS.next()){
                fila[0]= RS.getInt(1);
                fila[1]= RS.getString(2);                              
                fila[2]= RS.getInt(3);
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
    
    //mètode per eliminació d'objectius
    public int deleteObjectiu(String id){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnexio();
        int res = 0;
        
        //consultar per eliminar objectiu per id
        String SQL = "DELETE from objectius WHERE idObjectius=" +id;
        try{
            ps = con.prepareStatement(SQL);
            res =ps.executeUpdate();
            if(res>0){
                JOptionPane.showMessageDialog(null,"Objectiu esborrat");              
            }
            //excepció en cas d'error en l'eliminació
        }catch (SQLException e){
            System.err.println("Error al esborrar l'objectiu" + e.getMessage());
        }finally{
            ps= null;            
        }
        return res;
    }
}
