
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sigriid
 */
public class Client {
    public static void main(String[] args) {

        //Host del servidor
        final String HOST = "127.0.1.1";
        //Port del servidor
        final int PORT = 12245;
        DataInputStream in;
        DataOutputStream out;

        try {
            //Creem el socket per connectar amb el client 
            Socket sc = new Socket(HOST, PORT);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            //Enviem missatge
            out.writeUTF("Missatge de prova");

            //Rebem missatge
            String missatge = in.readUTF();

            System.out.println(missatge);

            sc.close();
            //tanquem connexió

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
    




    

