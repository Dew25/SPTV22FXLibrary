/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.adminpanel;

import entity.User;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import sptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AdminpanelController implements Initializable {

    private HomeController homeController;
    private User selectedUser;
    private Enum selectedRole;
    @FXML private ComboBox cbUsers;
    @FXML private ComboBox cbRoles;
    @FXML private Button btDeleteRole;
    @FXML private Button btAddRolle;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setHomeController(HomeController homeController) {
       this.homeController = homeController;
    }

    public void loadUsers() {
        cbUsers.setItems(FXCollections.observableArrayList(homeController.getApp().getEntityManager()
               .createQuery("SELECT u FROM User u WHERE u.login != :admin")
               .setParameter("admin", "admin")
               .getResultList()));
        cbUsers.setCellFactory(param -> new ListCell<User>(){
            @Override
            protected void updateItem(User user,boolean empty){
                super.updateItem(user, empty);
                if(user==null || empty){
                    setText(null);
                }else{
                    setText(user.getReader().getFirstname()
                                +" "
                                +user.getReader().getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray()));
                }
            }
        });
        cbUsers.setButtonCell(new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (user == null || empty) {
                        setText(null);
                    } else {
                        setText(user.getReader().getFirstname()
                                +" "
                                +user.getReader().getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray()));
                    }
                }
            });
        cbUsers.setOnAction(event->{
            this.selectedUser = (User) cbUsers.getValue();
            //System.out.println(selectedUser.toString());
            homeController.getLbInfo().setText("");
        });;
    }

    public void loadRoles() {
       cbRoles.setItems(FXCollections.observableArrayList(sptv22fxlibrary.SPTV22FXLibrary.ROLES.values()));
        //Настройка механизма отображения в ComboBox
        cbRoles.setCellFactory(param -> new ListCell<sptv22fxlibrary.SPTV22FXLibrary.ROLES>(){
            @Override
            protected void updateItem(sptv22fxlibrary.SPTV22FXLibrary.ROLES item,boolean empty){
                super.updateItem(item, empty);
                if(item==null || empty){
                    setText(null);
                }else{
                    setText(item.toString());
                }
            }
        });
        cbRoles.setButtonCell(new ListCell<sptv22fxlibrary.SPTV22FXLibrary.ROLES>() {
            @Override
            protected void updateItem(sptv22fxlibrary.SPTV22FXLibrary.ROLES item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        cbRoles.setOnAction(event->{
            this.selectedRole = (sptv22fxlibrary.SPTV22FXLibrary.ROLES) cbRoles.getValue();
            //System.out.println(selectedRole.toString());
            homeController.getLbInfo().setText("");
        });
    }
    public void clickBtAddRole(){
        if(!this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().add(this.selectedRole.toString());
            this.changeRole();
        }
    }
    public void clickBtDeleteRole(){
        if(this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().remove(this.selectedRole.toString());
            this.changeRole();
        }
    }
    private void changeRole(){
        homeController.getApp().getEntityManager().getTransaction().begin();
        homeController.getApp().getEntityManager().merge(this.selectedUser);
        homeController.getApp().getEntityManager().getTransaction().commit();
        this.loadUsers();
    }

}
