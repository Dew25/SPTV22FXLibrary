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
    private String nameOfTheResourceShown;
    public static int countShowInfo = 0;
    @FXML private Label lbHello;
    @FXML private Label lbInfo;
    @FXML private VBox vbContent;
    
    @FXML
    public void showRangepage(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            loginRun("showRangepage");
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.ADMINISTRATOR.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав просматривать рейтинг. Только для администраторов!"); 
            countShowInfo=1;
            listBooks();
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
            nameOfTheResourceShown = "showRangepage";
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
            loginRun("showAdminPanel");
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.ADMINISTRATOR.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав пользоваться панелью администратора. Только для администраторов!"); 
            countShowInfo=1;
            listBooks();
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
            nameOfTheResourceShown="showAdminPanel";
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
            loginWindow.showAndWait();
//           
        } catch (Exception e) {
            
            System.out.println("error: "+e);
        }
        
    }
    
    private void loginRun(){
        loginRun(nameOfTheResourceShown);
    }
    private void loginRun(String menuName){
        login();
        switch (menuName) {
            case "userProfile":
                userProfile();
                break;
            case "addNewBook":
                addNewBook();
                break;
            case "listUsers":
                listUsers();
                break;
            case "showAdminPanel":
                showAdminPanel();
                break;
            case "showRangepage":
                showRangepage();
                break;
            default:
                listBooks();
        }
        
    }
    
    @FXML
    private void addNewBook(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            loginRun("addNewBook");
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.MANAGER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав добавлять книги. Этот ресурс только для менеджеров!"); 
            countShowInfo=1;
            listBooks();
            return;
        }
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
            VBox vbNewBook = loader.load();
            NewbookController newbookController = loader.getController();
            newbookController.setHomeController(this);
            app.getPrimaryStage().setTitle("SPTV22FXLibrary - Добавление новой книги");
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewBook);
            nameOfTheResourceShown="addNewBook";
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
            nameOfTheResourceShown="addNewUser";
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void userProfile(){
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Войдите в программу со своим логином!"); 
            loginRun("userProfile");
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.USER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("Вы не вошли в программу!"); 
            countShowInfo=1;
            listBooks();
            return;
        }
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
            nameOfTheResourceShown="userProfile";
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
            loginRun("listUsers");
            return;
        }
        if(!sptv22fxlibrary.SPTV22FXLibrary.user.getRoles().contains(sptv22fxlibrary.SPTV22FXLibrary.ROLES.MANAGER.toString())){
            getLbInfo().getStyleClass().clear();
            getLbInfo().getStyleClass().add("infoError");
            getLbInfo().setText("У вас нет прав просматривать список пользователей. Только для менеджеров!"); 
            countShowInfo=1;
            listBooks();
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
            nameOfTheResourceShown="listUsers";
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
            loginRun("listBooks");
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
            if(countShowInfo < 1){
                this.lbInfo.setText("");
            }else{
                countShowInfo = 0;
            }
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbListBooks);
            nameOfTheResourceShown="listBooks";
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
            nameOfTheResourceShown="loadBooks";
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
