/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sptv22fxlibrary;


import admin.adminpanel.AdminpanelController;
import books.book.BookController;
import books.listbooks.ListbooksController;

import books.newbook.NewbookController;
import entity.Book;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import range.RangepageController;
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
    public void showRangepage(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.ADMINISTRATOR.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав на этот ресурс. Только для администраторов!"); 
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/range/rangepage.fxml"));
        try {
            VBox vbAdminpanelRoot = loader.load();
            RangepageController rangepageController = loader.getController();
            rangepageController.setHomeController(this);
            rangepageController.setListHistories();
            rangepageController.showRangeBooks();
            rangepageController.showRangeReaders();
            getApp().getPrimaryStage().setHeight(getApp().getPrimaryStage().getHeight()+70);
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbAdminpanelRoot);
            
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
    }
    @FXML
    public void showAdminPanel(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.ADMINISTRATOR.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав на этот ресурс. Только для администраторов!"); 
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/adminpanel/adminpanel.fxml"));
        try {
            VBox vbAdminpanelRoot = loader.load();
            AdminpanelController adminpanelController = loader.getController();
            adminpanelController.setHomeController(this);
            adminpanelController.loadUsers();
            adminpanelController.loadRoles();
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbAdminpanelRoot);
            
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
    }
        
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
        getLbInfo().getStyleClass().clear();
        getLbInfo().getStyleClass().add("info");
        getLbInfo().setText("");
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
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.MANAGER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав на этот ресурс. Только для менеджеров!"); 
            return;
        }
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
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.MANAGER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав на этот ресурс. Только для менеджеров!"); 
            return;
        }
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
    @FXML 
    private void listBooks(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.USER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав на этот ресурс. Только для пользователей!"); 
            return;
        }
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/listbooks/listbooks.fxml"));
            VBox vbListBooks = loader.load();
            ListbooksController listbooksController = loader.getController();
            listbooksController.setHomeController(this);
            listbooksController.loadBooks();
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Список книг");
            this.lbInfo.setText("");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbListBooks);
            
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

    void loadBooks() {
        List<Book> listBooks = app.getEntityManager()
                .createQuery("SELECT b FROM Book b")
                .getResultList();
        ObservableList books = FXCollections.observableArrayList(listBooks);
        HBox hbListBooks = new HBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/book/book.fxml"));
            VBox vbBook = loader.load();
            BookController bookController = loader.getController();
           
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
