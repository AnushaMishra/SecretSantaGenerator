/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am9fqsecretsanta;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anushamishra
 */
public abstract class Pairs {
    public String displayPairs(HashMap<String, String> pairs){
       String output = "";
       for(Map.Entry<String,String> entry : pairs.entrySet()){
           output  = output + entry.getKey() + " is paired with " + entry.getValue() + "\n";
       }
       return output;
    }
    public abstract String createSearchLink(String gift);
}
