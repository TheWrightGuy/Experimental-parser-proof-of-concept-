
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class FileManager {
    // Constants
    private final String NULL_TOKEN = "\0";

    // Attributes
    private BufferedReader TargetFile;
    private BufferedReader KnownLanguageList;
    private String targetFileNameSuffix;

     // Constructor
    FileManager(String targetFileName){
        // Set up Target File Reader
        try {
            TargetFile = new BufferedReader(new FileReader(targetFileName));
        } catch (FileNotFoundException e){
            System.out.println("Critical Error: Can not find " + targetFileName 
                + "\nExiting...");
                System.exit(11);
        } catch (IOException e) {
            System.out.println("Critical Error: IOException caught when accessing " 
                + targetFileName + ":\n");
            System.out.println(e);
            System.out.println("\nQuitting...");
            System.exit(10);
        }
        
        // Set up Known Programming Language File Type List Reader
        try {
            KnownLanguageList = new BufferedReader(new FileReader("knownFileTypes.txt"));
        } catch (FileNotFoundException e){
            System.out.println("Critical Error: Can not find core file: " + 
                "knownFileTypes.txt\nExiting...");
            System.exit(11);
        } catch (IOException e) {
            System.out.println("Critical Error: IOException caught when accessing" 
                + " knownFileTypes.txt:\n");
            System.out.println(e);
            System.out.println("\nQuitting...");
            System.exit(10);
        }

        // Store Suffix of Target File
        ArrayList<String> tokenedInputString 
            = new ArrayList<>(Arrays.asList(targetFileName.split(".")));
        this.targetFileNameSuffix = 
            tokenedInputString.get(tokenedInputString.size() - 1);
    }// End of FileManager(String targetFileName);

    // Public Methods
    public String findCommentToken(){
        String targetToken = getFileTypesCommentCharacter();
        if (targetToken.equals(NULL_TOKEN)){
            Scanner Reader = new Scanner(System.in);
            System.out.println("Unable to find ." + targetFileNameSuffix
                 + " file data. Please enter the character that starts a comment in a ." 
                 + targetFileNameSuffix + " file:");
            targetToken = Reader.nextLine();
            Reader.close();
            // TODO: Update knownFileTypes.txt
        } 
        return targetToken;
    }

    public void _FileManagerDestructor(){
        TargetFile.close();
        KnownLanguageList.close();
        System.err.println("Filestream closed.");
    }

    // Helper Methods
    private String getFileTypesCommentCharacter(){
        // Local Variables
        String discoveredToken = NULL_TOKEN;
        boolean isTokenFound = false;
        String lineIn = "garbage_Value";
        while(lineIn != null && !isTokenFound){
            String[] input = KnownLanguageList.readLine().split(" ");
            if (targetFileNameSuffix.equals(input[0])){
                discoveredToken = input[1];
                isTokenFound = true;
            }
        }
        return discoveredToken;
    }
}