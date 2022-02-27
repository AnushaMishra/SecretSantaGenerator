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
public interface FileHandling {
    public abstract List<String> convertToList(File f);
  //  public abstract HashMap<String, String> createPeopleKey(HashMap<String, String> peopleToGift);
  //  public abstract HashMap<String, String> associateGifts(List<String> entries);
    public abstract Boolean saveFile(HashMap<String, String> peopleToGifts, HashMap<String, String> peopleToPeople);
         
}
