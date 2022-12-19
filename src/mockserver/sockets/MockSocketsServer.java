/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mockserver.sockets;

import controller.ResponseMap;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Sigriid
 */
public class MockSocketsServer {
private int port;
    private ResponseMap m= new ResponseMap();
    private BufferedReader responsesFile;
    private PrintStream logFile;
    private final String ERROR_INITIALIZING="ERROR INITIALIZING";
    private final String COMMUNICATIONS_LOG="COMMUNICATIONS LOG";
    private final String INPUT_OUTPUT_ERROR="Input/Output error";
    private final String ANSWER="Answer";
    
    private int threadCount=0;
    
    
    // server thread
    private class RunnableListener implements Runnable{
            private volatile boolean running=false; // read only property
            private ServerSocket sk=null;
            
            /**
             * Stops server: closes socket used by run method and sets running property to false
             * to end run method execution
             */
            public void shutdown(){
                if(sk!=null && !sk.isClosed()) try {
                    sk.close();
                    logFile.close();        
                } catch (IOException ex) {
                    
                } finally{
                    running=false;
                }
            }
            /**
             * gets running property value
             * @return running property value
             */
            public boolean isRunning(){
                return running;
            }
            /**
             * Starts server. Server runs until shutdwon method is called.
             * Sets running property to true
             */
            @Override public synchronized void run(){        
                try {
                    running=true;
                    
                    sk = new ServerSocket(port);

                    while(running){
                        Socket client = sk.accept();
                        
                        BufferedReader input = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));

                        String data = input.readLine();

                        logFile.println(thisMoment()+" Received: \n"+data+"\n");
                        replay(client,data);  // sends response to client in a new thread and, in this way, continues listening clients

                    }
                } catch (IOException e) {
                    if(sk!=null && !sk.isClosed()) showIOErrorInformation(e, logFile); // if sk.isClosed can be a "Server stop" operation
                }
        }
    }
    
    
    
    
    private RunnableListener listener=new RunnableListener();
 
   
    /**
     * Creates a MockServer
     * @param port port to listen
     * @param responsesFile file with server responses
     * @param logFile file with execution result
     * @throws java.io.IOException if an I/O error occurs
     * @throws Error if files don't have a good structure
     */
    public void initialize (int port, String responsesFile, String logFile) throws IOException {
        
        this.port=port;
        
        this.responsesFile=new BufferedReader(new FileReader(responsesFile));
        this.logFile=new PrintStream(new FileOutputStream(logFile));

        boolean goodLoad=m.load(this.responsesFile,this.logFile);
        
        this.responsesFile.close();

        
        if(!goodLoad) throw new Error(ERROR_INITIALIZING);

        
    }
    
   

    
    /**
     * Launches server
     */
    public void run()  {
        
        logFile.println("\n\n\n\n\n\n\n");
        logFile.println(COMMUNICATIONS_LOG);  //writes tittle
        logFile.printf(new String(new char[COMMUNICATIONS_LOG.length()]).replace("\0", "-"));  // writes underscore of the tittle (at Java 11 can be done with repeat() )
        logFile.println();
        
        
        
        if(listener.isRunning()) return;
        
        Thread thread = new Thread(listener);
        thread.start();

        
    }
    // Sends response to client
    // Creates a new thread to allow server continue listening 
    private  void replay(Socket client, String data){
        Thread t=new Thread( new Runnable(){
            @Override
            public void run() {
                threadCount++;
                answer(client, data);
                threadCount--;
            }});
       
        t.start();
    }
    
    /**
     * stops server: stops main thread and waits until all secundary threads 
     * are finshed (each secundary thread just send a response to a client)
     */
    
    public void stop(){
        listener.shutdown();
        while(threadCount>0);
    }
    
    // sends server response corresponding to client request
    // if a client request has several possible responses, first is used
    // and removed
    // if doesn't exist response to request, default response are used
    // Also, log file is updated
    private void answer (Socket client, String data){
        List <String> texts;
        String textSent="";
        try{
            try{
               texts=m.get(data);
               if(texts==null||texts.isEmpty())  textSent=m.getDefaultResponse();
               else{
                   textSent=texts.get(0);
                   if(texts.size()>1)  texts.remove(0);
               }

               sendResponse(client,textSent);
               logFile.println(thisMoment()+" "+ANSWER+":\n"+textSent+" \n");
               
            } catch(IOException e){
                showIOErrorInformation(e, logFile);

            }finally{
                if(!client.isClosed()){
                    client.close();
                }
            }
        } catch(IOException e){
            System.err.println(thisMoment()+" "+INPUT_OUTPUT_ERROR+":"+e.getMessage());            
        }
    }
    
    private void showIOErrorInformation(Exception e, PrintStream logFile){
        logFile.println(INPUT_OUTPUT_ERROR+":"+e.getMessage());
    }
    
    // returns formatted current time in an String
    private String thisMoment(){
        return DateFormat.getDateTimeInstance().format(new Date());
    }
    
    /**
     * Get the value of logFile
     *
     * @return the value of logFile
     */
    public PrintStream getLogFile() {
        return logFile;
    }

    /**
     * Set the value of logFile
     *
     * @param logFile new value of logFile
     */
    public void setLogFile(PrintStream logFile) {
        this.logFile = logFile;
    }

    /**
     *  informs if server is running or not
     *  return  true if server is running, false otherwise
    **/
    public boolean isRunning(){
        return listener.isRunning();
    }
    

    /**
     * Get the value of responsesFile
     *
     * @return the value of responsesFile
     */
    public BufferedReader getResponsesFile() {
        return responsesFile;
    }

    /**
     * Set the value of responsesFile
     *
     * @param responsesFile new value of responsesFile
     */
    public void setResponsesFile(BufferedReader responsesFile) {
        this.responsesFile = responsesFile;
    }

    /**
     * send to client a message 
     * @param client socket open with the client
     * @param response server response to be sent to client

     * @throws IOException if a network error occurs
     */
    protected void sendResponse(Socket client, String response) throws IOException {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        
        output.println(response);

    } 
}
 

