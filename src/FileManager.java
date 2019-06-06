
/**
 * Contains file reader objects dealing with the contents 
 * of any files introduced to the program
 * 
 * Date Created: May 29th, 2019
 * Date Last Edited: May 29th, 2019
 * 
 * @author Logan C.W. Drescher
 * @author Bettina King-Smith
 * 
 * @version 0.0.1
 */

import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class FileManager {
    // Constants
    private final String NULL_TOKEN = "\0";
    private final String REF_FILE_NAME = "knownFileTypes.txt";


    // Attributes
    private BufferedReader TargetFile;
    private BufferedReader KnownLanguageList;
    private BufferedWriter LanguageAppender;
    private String fileCommentCode;
    private String targetFileNameSuffix;
    private boolean wasNewEntryCreated;

     // Constructor
    FileManager(String targetFileName){
        // Initalize Attributes
        fileCommentCode = NULL_TOKEN;
        wasNewEntryCreated = false;

        // Set up Target File Reader
        try {
            TargetFile = new BufferedReader(new FileReader(targetFileName));
        } catch (FileNotFoundException e){
            System.out.println("Critical Error: Can not find " + targetFileName 
                + "\nExiting...");
                System.exit(11);
        }
        
        // Set up Known Programming Language File Type List Reader
        try {
            KnownLanguageList = new BufferedReader(new FileReader(REF_FILE_NAME));
        } catch (FileNotFoundException e){
            System.out.println("Critical Error: Can not find core file: " + 
                "knownFileTypes.txt\nExiting...");
            System.exit(11);
        }
        

        // Store Suffix of Target File
        //TODO:: Fix stange error where targetFileName is lost by the time the Arraylist is accessed
        ArrayList<String> tokenedInputString 
            = new ArrayList<>(Arrays.asList(targetFileName.split("\\.")));
        this.targetFileNameSuffix = 
            tokenedInputString.get(tokenedInputString.size() - 1);

        // Prepare for analysis of target file;
        findCommentToken(targetFileNameSuffix);

    }// End of FileManager(String targetFileName);

    // Public Methods
    public String findCommentToken(String fileSuffix){
        String targetToken = getFileTypesCommentCharacter(fileSuffix);
        if (targetToken.equals(NULL_TOKEN)){
            Scanner Reader = new Scanner(System.in);
            Reader.reset();
            System.out.println("Unable to find ." + targetFileNameSuffix
                 + " file data. Please enter the character that starts a comment in a ." 
                 + targetFileNameSuffix + " file:");
            targetToken = Reader.nextLine();
            wasNewEntryCreated = true;
        } 
        // Prepare to append to known filetypes
        this.fileCommentCode = targetToken;
        return targetToken;
    }

    public void _FileManagerDestructor(){
        try{
            TargetFile.close();
            KnownLanguageList.close();
        }catch(IOException e){
            System.out.println("Fatal IO error closing files. Unknown effect. " +
            "Printing exception and Exiting...");
        }
        System.err.println("Filestream closed.");
        if(wasNewEntryCreated)
            addFileTypeToTextFile(targetFileNameSuffix, fileCommentCode);
    }

    @Override
    public String toString(){
        return "File type: " + targetFileNameSuffix + 
            "\nSingle Line Comment " + fileCommentCode;
    }

    // Helper Methods
    private String getFileTypesCommentCharacter(String suffix){
        // Local Variables
        String discoveredToken = NULL_TOKEN;
        boolean isTokenFound = false;
        String lineIn = "garbage_Value";
        while(lineIn != null && !isTokenFound){
            try{
                lineIn = (KnownLanguageList.readLine());
                String[] input = lineIn.split(" ");
                if (targetFileNameSuffix.equals(input[0])){
                    discoveredToken = input[1];
                    isTokenFound = true;
                }
            } catch (IOException e){
                System.out.println(e);
            } catch (NullPointerException e){
                continue;
            }
        }
        return discoveredToken;
    }

    private void addFileTypeToTextFile(String suffix, String commentCode){
        try {
            System.out.println("Comment code: " + commentCode);
            LanguageAppender = new BufferedWriter(new FileWriter(REF_FILE_NAME, true));
            LanguageAppender.write(suffix + " " + commentCode + "\n");
            LanguageAppender.flush();
            LanguageAppender.close();

        } catch (Exception e) {
            System.out.println("Error when writing to " + REF_FILE_NAME + "\n\nCheck For Corruption.");
            System.out.println("\n" + e);
        }
    }
}