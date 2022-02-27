/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am9fqsecretsanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anushamishra
 */
public class CSVHandling extends Pairs implements FileHandling{
    private Pairs one;
    @Override
    public List<String> convertToList(File f){
        //Beginning of code from: https://mkyong.com/java/java-how-to-read-a-file-into-a-list/
        List<String> result = new ArrayList<>();
	BufferedReader br = null;
	try {

		br = new BufferedReader(new FileReader(f));

		String line;
		while ((line = br.readLine()) != null) {
			result.add(line);
		}

	} catch (IOException e) {
	} finally {
		if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(CSVHandling.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
        //End of code from: https://mkyong.com/java/java-how-to-read-a-file-into-a-list/
        return result;
    }
    
    public HashMap<String, String> initKey(HashMap<String, String> peopleToGifts){
        HashMap<String, String> result = new HashMap<>();
        for(Map.Entry<String,String> entry : peopleToGifts.entrySet()){
            result.put(entry.getKey(),entry.getKey());
        }
        return result;
    }
    
        
    

    public HashMap<String, String> createPeopleKey(HashMap<String, String> peopleToGifts){
        
    Map<String, String> output = initKey(peopleToGifts);
    List<String> people = new ArrayList<>(output.values());
    List<String> pair = new ArrayList<>(output.values());
    Collections.shuffle(pair);
    
    //Make sure they are not the exact same
    while(people.equals(pair)){
        Collections.shuffle(pair);
        
    }
    //check if two lists have any elements in the same index
    for(int i = 0; i<people.size(); i++){
        if(people.get(i).equals(pair.get(i))){
            Collections.swap(pair, i, i+1);
        }
    }
    
    //Create hashmap
    for(int i = 0; i<people.size(); i++){
        output.put(people.get(i), pair.get(i));
    }

    return (HashMap) output;
    }
    

    public HashMap<String, String> associateGifts(List<String> entries){
        String person;
        String gift;
        Map<String, String> output = new HashMap<>();
        for (int i = 0; i < entries.size(); i++) {
            String line = entries.get(i);
            line = line.trim();
            if(i==0){
                person = line.substring(i+1, line.indexOf(",")).trim();
            }
            else{
            person = line.substring(0,line.indexOf(",")).trim();
            }
            
            gift = line.substring(line.indexOf(",")+1, line.length()).trim();
            output.put(person, gift);
        }
        
        return (HashMap) output;
    }
    
    @Override
    public String createSearchLink(String gift){
        String root = "https://www.amazon.com/s?k=";
        
        String query = gift.trim();
        //Removes multiple spaces in a row
        //Beginning of code from: https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        query = query.replaceAll("( )+ | +", "$1");
        //End of code from: https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        //removes non letters and turns them to spaces
        //Beginning of code from: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        query = query.replaceAll(
          "[^a-zA-Z0-9]", " ");
        //End of code from: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        //Turns single spaces to +
        query = query.replaceAll(
          " ", "+");

        root = root.concat(query);
        return root;
        
    }
    
    @Override
    public Boolean saveFile(HashMap<String, String> peopleToGifts, HashMap<String, String> peopleToPeople){
         try (PrintWriter writer = new PrintWriter(new File("savedpairs.csv"))) {
                for(Map.Entry<String,String> entry : peopleToPeople.entrySet()){
                    StringBuilder sb = new StringBuilder();
                     sb.append(entry.getKey());
                     sb.append(",");
                     sb.append(entry.getValue());
                     sb.append(",");
                     sb.append(peopleToGifts.get(entry.getValue()));
                     sb.append("\n");
                     writer.write(sb.toString());
                }
                writer.close();
                one.createSearchLink("Hello");
                return true;
                
         } 
         catch (FileNotFoundException e) {
            return false;
        }
    }
}
