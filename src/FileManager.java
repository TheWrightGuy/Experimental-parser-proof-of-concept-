
/**
 * Contains file reader objects dealing with the contents 
 * of any files introduced to the program
 * 
 * Date Created: May 29th, 2019
 * Date Last Edited: June 18th, 2019
 * 
 * @author Logan C.W. Drescher
 * @author Bettina King-Smith
 * 
 * @version 0.0.1
 */
/*
    This is an equivalent header for this document
 //@dateCreated May 29th, 2019
 //@dateEdited  June 18th, 2019
 //@author Logan C.W. Drescher
 //@author Bettina King-Smith
 //@version 0.0.1
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
    private final String REF_FILE_NAME = "knownFileTypes.txt";

    // Attributes
    private BufferedReader TargetFile;
    private BufferedReader KnownLanguageList;
    private BufferedWriter LanguageAppender;
    private ArrayList<String> fileCommentCode;
    private String targetFileNameSuffix;
    private boolean wasNewEntryCreated;

    // Constructor
    FileManager(String targetFileName){
        // Initalize Attributes
        fileCommentCode = null;
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
        ArrayList<String> tokenedInputString 
            = new ArrayList<>(Arrays.asList(targetFileName.split("\\.")));
        this.targetFileNameSuffix = 
            tokenedInputString.get(tokenedInputString.size() - 1);

        // Prepare for analysis of target file;
        findCommentToken(targetFileNameSuffix);

    }// End of FileManager(String targetFileName);

    // Public Methods
    public void findCommentToken(String fileSuffix){
        ArrayList<String> targetToken = getFileTypesCommentCharacter(fileSuffix);
        if (targetToken == null){
            Scanner Reader = new Scanner(System.in);
            Reader.reset();
            System.out.println("Unable to find ." + targetFileNameSuffix
                 + " file data. Please enter a list of character sequences that start a comment in a ." 
				 + targetFileNameSuffix + " file, separated by spaces:");
			String[] readerIn = Reader.nextLine().split(" ");
            targetToken = new ArrayList<>(Arrays.asList(readerIn));
            wasNewEntryCreated = true;
        } 
        // Prepare to append to known filetypes
        this.fileCommentCode = targetToken;
        return;
    }

	//TODO: Update to work with vector
    public String[] getTagsFromCode(){
        ArrayList<String> tags = new ArrayList<>();
        try {
            String tmp = TargetFile.readLine();
            while(tmp != null){
				if(containedIn(tmp))
                	tags.add(tmp);
                tmp = TargetFile.readLine();
            }
        } catch (IOException e){
            System.out.println("Error: IO Exception when reading source code.");
            System.exit(12);
        }
        String[] convArray = new String[tags.size()];
        return tags.toArray(convArray);
    }

	public ArrayList<String> getTagsFromCode(ArrayList<String> output){
        ArrayList<String> tags = new ArrayList<>();
        try {
            String tmp = TargetFile.readLine();
            while(tmp != null){
				if(containedIn(tmp))
                	tags.add(tmp);
                tmp = TargetFile.readLine();
            }
        } catch (IOException e){
            System.out.println("Error: IO Exception when reading source code.");
            System.exit(12);
        }
        output = new ArrayList<>(tags);
        return tags;
    }


    public void _FileManagerDestructor(){
        try{
            TargetFile.close();
            KnownLanguageList.close();
        }catch(IOException e){
            System.out.println("Fatal IO error closing files. Unknown effect. " +
			"Printing exception and Exiting...");
			System.out.println(e);
			System.exit(12);
        }
        System.err.println("Filestream closed.");
        if(wasNewEntryCreated)
            addFileTypeToTextFile(targetFileNameSuffix, concat(fileCommentCode));
    }

    @Override
    public String toString(){
        return "File type: " + targetFileNameSuffix + 
            "\nSingle Line Comment " + concat(fileCommentCode);
    }

    // Helper Methods
    private ArrayList<String> getFileTypesCommentCharacter(String suffix){	
        // Local Variables
        
        boolean isTokenFound = false;
        String lineIn = "garbage_Value";
        while(lineIn != null && !isTokenFound){
            try{
                lineIn = (KnownLanguageList.readLine());
				String[] in = lineIn.split(" ");
				ArrayList<String> input = new ArrayList<>(Arrays.asList(in));
                if (targetFileNameSuffix.equals(input.remove(0))){
					isTokenFound = true;
					ArrayList<String> discoveredToken = new ArrayList<>();
					for(String s : input){
						discoveredToken.add(s);
					}
                }
            } catch (IOException e){
                System.out.println(e);
            } catch (NullPointerException e){
                continue;
            }
        }
        return null;
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
	
	private String concat(ArrayList<String> arr){
		if (arr == null){
			return null;
		}
		String commentStr = "";
		for(String s : arr){
			commentStr += (s + " ");
		}
		return commentStr;
	}

	private boolean containedIn(String code){
		for(String comp : fileCommentCode){
			if(code.contains(comp + "@")){
				return true;
			}
		}
		return false;
	}
}