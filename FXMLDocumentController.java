/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am9fqsecretsanta;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author anushamishra
 */
public class FXMLDocumentController implements Initializable {
    
    
    
    private SecretSantaModel model;
    
    private String query = "";
    
    @FXML
    private Label fileStatus;
    
    @FXML
    private TabPane tabpane;
    
    @FXML
    private Button upload;
    
    @FXML
    private Button generate;
    
    @FXML
    private Hyperlink shopLink;
    
    @FXML
    private Text shopInfo;
    
    @FXML
    private TextField preferBox;
    
    @FXML
    private Button save;
    
    @FXML
    private void uploadCSV(ActionEvent event) {
        FileChooser fc = new FileChooser();
        Stage s = (Stage) tabpane.getScene().getWindow();
        fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
        File f = fc.showOpenDialog(s);
        model = new SecretSantaModel(f);
        upload.setDisable(true);
        fileStatus.setText("File Uploaded!");
        generate.setDisable(false);
    }
    
    private void shoppingSectionVisibility(Boolean visible){
        shopLink.setVisible(visible);
        shopInfo.setVisible(visible);
        preferBox.setVisible(visible);
        save.setVisible(visible);
    }
    
    @FXML
    private void generatePairs(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION); 
        alert.setTitle("Generated Pairs"); 
        alert.setHeaderText("Here are your generated pairs!");
        alert.setContentText(model.displayPairs(model.retrievePairs()));
        alert.showAndWait();
        shoppingSectionVisibility(true);
    }
    private void searchError(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There was an error");
        alert.setContentText("There was an error. The name you enter is either not a registered participant or was misspelled. Try again. This query is case-sensitive.");
        alert.showAndWait();
    }
    @FXML
    private void searchForGift(KeyEvent event) throws URISyntaxException, IOException{
        if (event.getCode().equals(KeyCode.ENTER)){
            if(Objects.nonNull(model.getPreference(preferBox.getText()))){
                String temp = preferBox.getText();
                query = model.getPreference(temp);
                shopLink.setText("Click here!");
            }
            else{
                //Create error dialog
                //Clear text field
                searchError();
                preferBox.clear();
            }
        }
    }
    private void resetSearchByName(){
        query = "";
        shopLink.setText("Start Shopping");
        preferBox.clear();
    }
    
    @FXML
    private void openLink(ActionEvent event) throws URISyntaxException, IOException{
            Desktop.getDesktop().browse(new URI(model.createSearchLink(query)));
            resetSearchByName();
    }
    private void saveSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success!");
        alert.setContentText("Your pairings and wishlist for each recipient were successfully saved in a file called savedpairs.csv!");
        alert.show();
    }
    
    private void saveError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Your data is not able to be saved properly at this time.");
        alert.show();
    }
    
    @FXML
    private void savePairs(ActionEvent event){
        Boolean success = model.saveFile(model.retrieveWishlist(), model.retrievePairs());
        if(success){
            saveSuccess();
        }
        else{
            saveError();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        generate.setDisable(true);
        generate.setStyle("-fx-background-color: green;");
        save.setStyle("-fx-background-color: darkred;");
        shoppingSectionVisibility(false);
    }    
    
}
