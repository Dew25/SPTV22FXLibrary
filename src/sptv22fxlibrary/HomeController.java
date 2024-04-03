/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sptv22fxlibrary;

import books.newbook.NewbookController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import users.newuser.NewuserController;

/**
 *
 * @author admin
 */
public class HomeController implements Initializable {
    private SPTV22FXLibrary app;
    
    @FXML private Label lbHello;
    @FXML private Label lbInfo;
    @FXML private VBox vbContent;
    
    @FXML
    private void addNewUser(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox vbNewUserRoot = loader.load();
            NewuserController newuserController = loader.getController();
            newuserController.setHomeController(this);
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Р”РѕР±Р°РІР»РµРЅРёРµ РЅРѕРІРѕРіРѕ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewUserRoot);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void addNewBook(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
            VBox vbNewBookRoot = loader.load();
            NewbookController newbookController = loader.getController();
            newbookController.setHomeController(this);
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Р”РѕР±Р°РІР»РµРЅРёРµ РЅРѕРІРѕР№ РєРЅРёРіРё");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewBookRoot);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbHello.setText("Добро пожаловать в библиотеку!");
       
    }    

    void setApp(SPTV22FXLibrary app) {
        this.app = app;
    }

    public SPTV22FXLibrary getApp() {
        return app;
    }

    public Label getLbInfo() {
        return lbInfo;
    }
    
}
