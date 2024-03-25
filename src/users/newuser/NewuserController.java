/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.newuser;

import entity.Reader;
import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import sptv22fxlibrary.HomeController;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class NewuserController implements Initializable {
    
    private HomeController homeController;
    
    @FXML private Button addNewUser;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    
    /**
     * Initializes the controller class.
     */
    
    @FXML private void clickAddNewUser(){
        Reader reader = new Reader();
        reader.setFirstname(tfFirstName.getText());
        reader.setLastname(tfLastName.getText());
        reader.setPhone(tfPhone.getText());
        User user = new User();
        user.setLogin(tfLogin.getText());
        PassEncrypt pe = new PassEncrypt();
        user.setPassword(pe.getEncryptPassword(tfPassword.getText(),pe.getSalt()));
        user.setReader(reader);
        user.getRoles().add(sptv22fxlibrary.SPTV22FXLibrary.ROLES.USER.toString());
        try {
            homeController.getApp().getEntityManager().getTransaction().begin();
            homeController.getApp().getEntityManager().persist(reader);
            homeController.getApp().getEntityManager().persist(user);
            homeController.getApp().getEntityManager().getTransaction().commit();
            homeController.getLbInfo().setText("Пользователь успешно добавлен");
            tfFirstName.setText("");
            tfLastName.setText("");
            tfPhone.setText("");
            tfLogin.setText("");
            tfPassword.setText("");
        } catch (Exception e) {
            homeController.getLbInfo().setText("Добавить пользователя не удалось");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
}
