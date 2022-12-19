import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
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
public class Servidor {
    
public static void main(String[] args) {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;

        //port del servidor
        final int PORT = 12245;

        try {
            //Creem el socket del servidor
            servidor = new ServerSocket(PORT);
            System.out.println("Servidor iniciat");

            //Sempre estarà escoltant peticions
            while (true) {

                //Esperem que un client es connecti
                sc = servidor.accept();

                System.out.println("Client connectat");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                //Llegim missatge
                String missatge = in.readUTF();

                System.out.println(missatge);

                //Enviem missatge
                out.writeUTF("Missatge des del servidor");

                //Tanquem socket
                sc.close();
                System.out.println("Client desconectat");

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
