/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigriid
 */
public class TestClient {
    private static int port;
    private static String ipAddress;
    private static int errorsCount;

    private static int lineStartItem;
    private static int inputLines;
    
    private static BufferedReader responsesFile;
    private static PrintStream logFile;
    private static final String ERROR_COMPARING="Error comparing answers at call ";
    private static final String EXPECTED="Expected:";
    private static final String RECEIVED="Received:";
    private static final String TOTAL_ERRORS_AND_MESSAGES="Errors/Total messages:";
    private static final String IO_ERROR="Input/Output error";
    public static final String DEFAULT_RESPONSE = "DEF:";
    public static final String COMMENT = "COM:";    
    public static final String CLIENT_MESSAGE = "CLI:";
    public static final String SERVER_RESPONSE = "SRV:";
    private static final String ERROR_REPETEATED_SECTION="Error: repeated section at line ";
    private static final String ERROR_WRONG_SECTION="Error: wrong section at line ";
    private static final String HEADER="CLIENT EXECUTION RESULT";
    
    
    private static int serverCalls;
    
    /**
     * Executes a client test
     * @param ip server ip address
     * @param serverPort server port
     * @param responsesFileName file with expected server responses
     * @param logFileName file with execution result
     * @throws java.io.IOException if an I/O error occurs
     */
    public static void run (String ip, int serverPort, String responsesFileName, String logFileName) throws IOException {
        
        String clientMessage=null;
        String serverResponse=null;
        String currentLine;
        
        ipAddress=ip;
        
        serverCalls=0;
        errorsCount=0;


        inputLines=0;
        
        port=serverPort;
        
        try{

            responsesFile=new BufferedReader(new FileReader(responsesFileName));
            logFile=new PrintStream(new FileOutputStream(logFileName));
            
            logFile.println(HEADER);  //writes tittle
            logFile.printf(new String(new char[HEADER.length()]).replace("\0", "-"));  // writes underscore of the tittle (at Java 11 can be done with repeat() )
            logFile.println();
            
            while((currentLine=responsesFile.readLine())!=null){
                inputLines++;
                String lineCap=currentLine.toUpperCase();
                
                if(lineCap.equals(CLIENT_MESSAGE)){
                    lineStartItem=inputLines;
                    if(clientMessage!=null){
                        printError(ERROR_REPETEATED_SECTION, lineStartItem, logFile);
                        readLines(responsesFile);  //reads and skips section
                    }
                    else {
                        clientMessage=readLines(responsesFile);
                        if(serverResponse!=null){
                            talkToServer(clientMessage, serverResponse); //sends message to server and checks response
                            clientMessage=null;
                            serverResponse=null;
                        }
                    }
                }
                else if(lineCap.equals(SERVER_RESPONSE)){
                    lineStartItem=inputLines;
                    if(serverResponse!=null){
                        printError(ERROR_REPETEATED_SECTION, lineStartItem, logFile);
                        readLines(responsesFile);  //reads and skips section                    
                    }
                    else {
                        serverResponse=readLines(responsesFile);
                        if(clientMessage!=null){
                            talkToServer(clientMessage, serverResponse);//sends message to server and checks response
                            clientMessage=null;
                            serverResponse=null; 
                        }
                    }
                }else if(lineCap.equals(COMMENT)){
                    readLines(responsesFile);  //reads and skips section
                }else{
                    lineStartItem=inputLines;
                    if(!lineCap.equals(DEFAULT_RESPONSE)) printError(ERROR_WRONG_SECTION, lineStartItem, logFile);
                    readLines(responsesFile);  //reads and skips section
                }
                
            }
            responsesFile.close();
            logFile.println(TOTAL_ERRORS_AND_MESSAGES+errorsCount+"/"+serverCalls);
            logFile.close();        
        } catch (IOException ex) {
           throw new IOException(IO_ERROR+"\n"+ex.getMessage());
        }
    }
    /**
     * Sends message to server and checks if its response is as expected
     * @param clientMessage message to be sent to the server
     * @param serverResponse expected server response
     * @throws IOException if an IO error occurs
    */
    public static void talkToServer(String clientMessage, String serverResponse) throws IOException{
        
        Socket socket = new Socket(ipAddress, port);
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        serverCalls++;
        output.println(clientMessage);
                
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
           
        String data = input.readLine();

        if(!data.equals(serverResponse)){
            errorsCount++;
            logFile.println(thisMoment()+" "+ERROR_COMPARING+serverCalls);
            logFile.println(EXPECTED+serverResponse);
            logFile.println(RECEIVED+data);
            logFile.println();
        }
                
        socket.close();
    }
    
   
    // Prints a line in the log file with text and linesStartItem information
    private static void printError(String text, int lineStartItem, PrintStream logFile) {
        logFile.println(thisMoment()+" "+text+lineStartItem);
    }

   // Reads and returns section
    
    private static String readLines(BufferedReader responsesFile) {
        try {
            StringBuilder result=new StringBuilder();
            String currentLine;
            
            while((currentLine=responsesFile.readLine())!=null){
                inputLines++;
                if(currentLine.length()!=0){
                    result.append(currentLine);
                } else break;
            }
            
            return result.toString();
        } catch (IOException ex) {

            return null;
        }
    }
    
   
    // Formats current time

    private static String thisMoment(){
        return DateFormat.getDateTimeInstance().format(new Date());
    }

}



