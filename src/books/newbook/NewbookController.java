/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.newbook;

import entity.Author;
import entity.Book;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author user
 */
public class NewbookController implements Initializable {
    private HomeController homeController;
    private Stage authorsWindow;
    @FXML private TextField tfTilte;
    @FXML private ListView lvAuthors;
    @FXML private TextField tfPublishedYear;
    @FXML private TextField tfQuantity;
    
    
    @FXML private void clickAddAuthors(){
        List<Author> listAuthors =  homeController.getApp().getEntityManager()
                .createQuery("SELECT a FROM Author a")
                .getResultList();
        ObservableList<Author> authors = FXCollections.observableArrayList(listAuthors);
        // Создаем список для выбора с возможностью множественного выбора
        ListView<Author> listView = new ListView<>(authors);
        listView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Кнопка для получения выбранных авторов
        Button selectButton = new Button("Выбрать");
        selectButton.setOnAction(event -> {
            ObservableList<Author> selectedAuthors = listView.getSelectionModel().getSelectedItems();
            System.out.println("Выбранные авторы: " + selectedAuthors);
            lvAuthors = new ListView<>(selectedAuthors);
        });

        // Создаем контейнер VBox и добавляем в него список авторов и кнопку
        VBox root = new VBox(listView, selectButton);

        authorsWindow = new Stage();
        authorsWindow.setTitle("Авторы");
        authorsWindow.initModality(Modality.WINDOW_MODAL);
        authorsWindow.initOwner(homeController.getApp().getPrimaryStage());
        // Создаем сцену и устанавливаем контейнер VBox как корневой узел
        Scene scene = new Scene(root, 300, 250);
        authorsWindow.setScene(scene);
        authorsWindow.setTitle("Выбор авторов");
        authorsWindow.show();
    }
    @FXML private void clickAddNewBook(){
        if(tfTilte.getText().isEmpty() 
                || lvAuthors.getItems().isEmpty()
                || tfPublishedYear.getText().isEmpty() 
                || tfQuantity.getText().isEmpty()){
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("infoError");
            homeController.getLbInfo().setText("Заполните все поля");
            return;
        }
        
    }
    
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
    
}
