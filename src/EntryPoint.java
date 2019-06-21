/**
 * Holds the main() method, and entry point tools (if necessary)
 * 
 * Date Created: May 29th, 2019
 * Date Last Edited: May 29th, 2019
 * 
 * @author Logan C.W. Drescher
 * @author Bettina King-Smith
 * 
 * @version 0.0.1
 */

 import java.util.ArrayList;


class EntryPoint {
	public static void main(String args[]){
		ArrayList<String> tags = new ArrayList<>();
		// Testing
		FileManager mang = new FileManager("HelloWorld.cpp");
		System.out.println(mang);
		tags = mang.getTagsFromCode(tags);
		System.out.println("\nTags:");
		for(String x : tags) System.out.println(x);
		mang._FileManagerDestructor();
		mang = new FileManager("FileManager.java");
		System.out.println(mang);
		tags = mang.getTagsFromCode(tags);
		System.out.println("\nTags:");
		for(String x : tags) System.out.println(x);
		mang._FileManagerDestructor();
	}
}