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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import users.listusers.ListusersController;
import users.loginform.LoginformController;
import users.newuser.NewuserController;
import users.userprofile.UserprofileController;

/**
 *
 * @author admin
 */
public class HomeController implements Initializable {
    private SPTV22FXLibrary app;
    private Stage loginWindow;
    @FXML private Label lbHello;
    @FXML private Label lbInfo;
    @FXML private VBox vbContent;
    
    @FXML
    public void login(){
        loginWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/loginform/loginform.fxml"));
        try {
            VBox vbLoginFormRoot = loader.load();
            LoginformController loginformController = loader.getController();
            loginformController.setHomeController(this);
            Scene scene = new Scene(vbLoginFormRoot,401,180);
            loginWindow.setTitle("Вход");
            loginWindow.initModality(Modality.WINDOW_MODAL);
            loginWindow.initOwner(getApp().getPrimaryStage());
            loginWindow.setScene(scene);
            loginWindow.show();
            
        } catch (Exception e) {
            
            System.out.println("error: "+e);
        }
        
    }
    
    @FXML
    private void addNewUser(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox vbNewUser = loader.load();
            NewuserController newuserController = loader.getController();
            newuserController.setHomeController(this);
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Добавление нового пользователя");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewUser);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void userProfile(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/userprofile/userprofile.fxml"));
            VBox vbUserProfileRoot = loader.load();
            UserprofileController userprofileController = loader.getController();
            userprofileController.setHomeController(this);
             try {
                userprofileController.loadUser();
             } catch (Exception e) {
                getLbInfo().getStyleClass().clear();
                getLbInfo().getStyleClass().add("infoError");
                getLbInfo().setText("Войдите в программу со своим логином!");  
                return;
             }
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Профиль пользователя");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbUserProfileRoot);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void addNewBook(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
            VBox vbNewBook = loader.load();
            NewbookController newbookController = loader.getController();
            newbookController.setHomeController(this);
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Добавление новой книги");
            this.lbInfo.setText("");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewBook);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML 
    private void listUsers(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/listusers/listusers.fxml"));
            VBox vbListUsers = loader.load();
            ListusersController listusersController = loader.getController();
            listusersController.setHomeController(this);
            listusersController.loadUsers();
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Список пользователей");
            this.lbInfo.setText("");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbListUsers);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbHello.setText("Добро пожаловать в нашу библиотеку!");
       
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

    public Stage getLoginWindow() {
        return loginWindow;
    }
    
}
