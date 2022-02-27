/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am9fqsecretsanta;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author anushamishra
 */
public class SecretSantaModel extends CSVHandling{
    private HashMap<String, String> personToPerson;
    private HashMap<String, String> personToGift;
    private File data;
    private List<String> dataList;
    
    SecretSantaModel(File f){
        data = f;
        dataList = super.convertToList(data);
        personToGift = super.associateGifts(dataList);
        personToPerson = super.createPeopleKey(personToGift); 
    }
   
    //Retrieve hashmap containing person to match pairs
    public HashMap<String, String> retrievePairs(){
       return personToPerson;
    }
    //Retrieve hashmap containing person to gift preference pairs
    public HashMap<String, String> retrieveWishlist(){
        return personToGift;
    }
    
    //This method returns the gift preference of one person entered 
    public String getPreference(String name){
        return personToGift.get(name);
    }
}
