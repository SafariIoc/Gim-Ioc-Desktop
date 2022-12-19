/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sigriid
 */
public class ResponseMap extends HashMap<String,List<String>>{

    private String defaultResponse=null;  // default response property
    public final String DEFAULT_RESPONSE = "DEF:";
    public final String CLIENT_MESSAGE = "CLI:";
    public final String SERVER_RESPONSE = "SRV:";
    public static final String COMMENT = "COM:";
    
    private final String ERROR_REPETEATED_SECTION="ERROR: repeated section at line ";
    private final String ERROR_NO_ENTRIES="ERROR: no entries. At line ";
    private final String ERROR_WRONG_SECTION="Error: wrong section at line ";
    private final String FILE_LOAD="FILE LOAD";
    private int inputLines;
    
    /**
     * Reads response files and loads responses in ResponseMap
     * @param responsesFile input file with responses
     * @param logFile log file
     * @return true if operation ok, false otherwise
     */
    
    public boolean load(BufferedReader responsesFile,PrintStream logFile){


        int lineStartItem;
        String clientMessage=null, serverResponse=null;
        String currentLine;
        boolean result=true;

        defaultResponse=null;
        clear();        
        
        inputLines=0;
        
        logFile.println(FILE_LOAD);  //writes tittle
        logFile.printf(new String(new char[FILE_LOAD.length()]).replace("\0", "-"));  // writes underscore of the tittle (at Java 11 can be done with repeat() )
        logFile.println();
        
        
        
        
        try {

            while((currentLine=responsesFile.readLine())!=null){
                inputLines++;
                String lineCap=currentLine.toString().toUpperCase();
                
                if(lineCap.equals(DEFAULT_RESPONSE)){
                    lineStartItem=inputLines;
                    if(defaultResponse!=null) {
                        printError(this.ERROR_REPETEATED_SECTION, lineStartItem, logFile);
                        readLines(responsesFile);  //reads and skips section
                    }
                    else defaultResponse=readLines(responsesFile);  //sets defaultResponse property
                }
                else if(lineCap.equals(CLIENT_MESSAGE)){
                    lineStartItem=inputLines;
                    if(clientMessage!=null){
                        printError(this.ERROR_REPETEATED_SECTION, lineStartItem, logFile);
                        readLines(responsesFile);  //reads and skips section
                    }
                    else {
                        clientMessage=readLines(responsesFile);  //reads response
                        if(serverResponse!=null){  // if server response has been read
                            this.add(clientMessage, serverResponse); //adds (received client message, server response) pair to the ReposeMap
                            clientMessage=null;
                            serverResponse=null;
                        }
                    }
                }
                else if(lineCap.equals(SERVER_RESPONSE)){
                    lineStartItem=inputLines;
                    if(serverResponse!=null){
                        printError(this.ERROR_REPETEATED_SECTION, lineStartItem, logFile);
                        readLines(responsesFile);  //read and skip section
                    }
                    else {
                        serverResponse=readLines(responsesFile);
                        if(clientMessage!=null){  // if received client message has been read
                            this.add(clientMessage, serverResponse);  //adds (received client message, server response) pair to the ResponseMap
                            clientMessage=null;
                            serverResponse=null;
                        }
                    }
                }else if(lineCap.equals(COMMENT)){
                    readLines(responsesFile);  //reads and skips section
                }else{
                    lineStartItem=inputLines;
                    printError(this.ERROR_WRONG_SECTION, lineStartItem, logFile);
                    readLines(responsesFile);  //read and skip section
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ResponseMap.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        if(defaultResponse!=null){
            setDefaultResponse(defaultResponse);
        }else if(this.isEmpty()) {
            printError(ERROR_NO_ENTRIES,inputLines,logFile);
            result=false;
        }
        
        return result;

    }

    /**
     * Get the value of defaultResponse
     *
     * @return the value of defaultResponse
     */
    public String getDefaultResponse() {
        return defaultResponse;
    }

    /**
     * Set the value of defaultResponse
     *
     * @param defaultResponse new value of defaultResponse
     */
    public void setDefaultResponse(String defaultResponse) {
        this.defaultResponse = defaultResponse;
    }

    
    /**
     * Default constructor
     */
    public ResponseMap() {
    }

    private void printError(String text, int lineStartItem, PrintStream logFile) {
        logFile.println(text+lineStartItem);
    }

    //  it reads lines from responsesFile until an empty line is found and 
    // returns a string whith the concatenation of read lines
    private String readLines(BufferedReader responsesFile) {
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
            Logger.getLogger(ResponseMap.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Adds a (received client message, server response) pair to the ResponseMap
     * if clientMessage exists in the map, server response is added at the end of this entry
     * otherwise a new entry is created.
     * @param clientMessage  client message that identifies entry
     * @param serverResponse server response to be add
     */
    public void add(String clientMessage, String serverResponse){
        List <String> entry;
        if((entry=this.get(clientMessage))==null){
            entry=new ArrayList<>();
            this.put(clientMessage, entry);
        }
        entry.add(serverResponse);
    }

    


    

}
