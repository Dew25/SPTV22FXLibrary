/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.loginform;

import entity.User;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import sptv22fxlibrary.HomeController;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class LoginformController implements Initializable {
    private HomeController homeController;
    @FXML private Label lbLoginInfo;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private Button btGoToLogin;
    /**
     * Initializes the controller class.
     */
    
    @FXML private void clickLogin(){
        String ligin = tfLogin.getText();
        String password = tfPassword.getText();
        try {
            User user = (User) homeController.getApp().getEntityManager().createQuery("SELECT u FROM User u WHERE u.login=:login")
                .setParameter("login", ligin)
                .getSingleResult();
            PassEncrypt pe = new PassEncrypt();
            String encriptPass = pe.getEncryptPassword(password, pe.getSalt());
            if(!user.getPassword().equals(encriptPass)){
                lbLoginInfo.setText("Пароли не совпадают");
                System.out.println("Пароли не совпадают");
                return;
            }
            sptv22fxlibrary.SPTV22FXLibrary.user=user;
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("info");
            homeController.getLbInfo().setText("Вы вошли как "+user.getLogin());
            homeController.getLoginWindow().close();
        } catch (Exception e) {
            lbLoginInfo.setText("Нет такого пользователя");
            System.out.println("error: "+e);
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfLogin.setPromptText("Логин");
        tfPassword.setPromptText("Пароль");
        tfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickLogin();
            }
        });

        // Обработчик события для Button
        btGoToLogin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btGoToLogin.fire();
            }
        });
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
}
