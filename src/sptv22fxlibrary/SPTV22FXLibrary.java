/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sptv22fxlibrary;

import entity.Reader;
import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.PassEncrypt;

/**
 *
 * @author admin
 */
public class SPTV22FXLibrary extends Application {

    private Stage primaryStage;
    public static enum ROLES {ADMINISTRATOR, MANAGER, USER};
    public static User user;
    private final EntityManager entityManager;

    public SPTV22FXLibrary() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SPTV22FXLibraryPU");
        this.entityManager = emf.createEntityManager();
        checkSuperUser();
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SPTV22FXLibrary");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    private void checkSuperUser() {
        if(!(getEntityManager().createQuery("SELECT u FROM User u").getResultList().size()>0)){
            User admin = new User();
            admin.setLogin("admin");
            PassEncrypt pe =  new PassEncrypt();
            admin.setPassword(pe.getEncryptPassword("12345", pe.getSalt()));
            admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
            admin.getRoles().add(ROLES.MANAGER.toString());
            admin.getRoles().add(ROLES.USER.toString());
            Reader reader = new Reader();
            reader.setFirstname("Juri");
            reader.setLastname("Melnikov");
            reader.setPhone("5654456565");
            getEntityManager().getTransaction().begin();
                getEntityManager().persist(reader);
                admin.setReader(reader);
                getEntityManager().persist(admin);
            getEntityManager().getTransaction().commit();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
