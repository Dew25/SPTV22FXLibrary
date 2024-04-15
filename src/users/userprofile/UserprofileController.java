/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.userprofile;

import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sptv22fxlibrary.HomeController;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class UserprofileController implements Initializable {

    private HomeController homeController;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;

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

    public void loadUser() throws Exception {
        if(sptv22fxlibrary.SPTV22FXLibrary.user == null){
            throw new Exception();
        }
        homeController.getLbInfo().setText("");
        tfFirstName.setText(sptv22fxlibrary.SPTV22FXLibrary.user.getReader().getFirstname());
        tfLastName.setText(sptv22fxlibrary.SPTV22FXLibrary.user.getReader().getLastname());
        tfPhone.setText(sptv22fxlibrary.SPTV22FXLibrary.user.getReader().getPhone());
        tfLogin.setText(sptv22fxlibrary.SPTV22FXLibrary.user.getLogin());
        
    }
    @FXML private void clickEditProfile(){
        try {
            User user = homeController.getApp().getEntityManager().find(User.class, sptv22fxlibrary.SPTV22FXLibrary.user.getId());
            user.getReader().setFirstname(tfFirstName.getText().trim());
            user.getReader().setLastname(tfLastName.getText().trim());
            user.getReader().setPhone(tfPhone.getText().trim());
            if(!tfPassword.getText().isEmpty()){
                PassEncrypt pe = new PassEncrypt();
                String encriptPass = pe.getEncryptPassword(tfPassword.getText().trim(), pe.getSalt());
                user.setPassword(encriptPass);
            }
            homeController.getApp().getEntityManager().getTransaction().begin();
            homeController.getApp().getEntityManager().merge(user.getReader());
            homeController.getApp().getEntityManager().merge(user);
            homeController.getApp().getEntityManager().getTransaction().commit();
            sptv22fxlibrary.SPTV22FXLibrary.user=user;
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("info");
            homeController.getLbInfo().setText("Профиль изменен!");
            tfPassword.setText("");
        } catch (Exception e) {
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("infoError");
            homeController.getLbInfo().setText("Изменить профиль не удалось");
            homeController.login();
        }
        
    }
    
}
