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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
        authorsWindow = new Stage();
        List<Author> listAuthors =  homeController.getApp().getEntityManager()
                .createQuery("SELECT a FROM Author a")
                .getResultList();
        ObservableList<Author> authors = FXCollections.observableArrayList(listAuthors);
        // Создаем список для выбора с возможностью множественного выбора
        ListView<Author> listViewAuthorsWindow = new ListView<>(authors);
        // Настройка отображения названия книги в ListView
        listViewAuthorsWindow.setCellFactory((ListView<Author> param) -> new ListCell<Author>() {
            @Override
            protected void updateItem(Author author, boolean empty) {
                super.updateItem(author, empty);
                if (author != null) {
                    setText(author.getFistname()+" "+author.getLastname());
                } else {
                    setText(null);
                }
            }
        });
        listViewAuthorsWindow.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Кнопка для получения выбранных авторов
        Button selectButton = new Button("Выбрать");
        selectButton.setOnAction(event -> {
            ObservableList<Author> selectedAuthors = listViewAuthorsWindow.getSelectionModel().getSelectedItems();
            
            lvAuthors.getItems().addAll(selectedAuthors);
            authorsWindow.close();
        });

        // Создаем контейнер VBox и добавляем в него список авторов и кнопку
        VBox root = new VBox(listViewAuthorsWindow, selectButton);

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
        List<Author> bookAuthors = new ArrayList<>();
        for (int i = 0; i < lvAuthors.getItems().size(); i++) {
            Author author = (Author) lvAuthors.getItems().get(i);
            bookAuthors.add(homeController.getApp().getEntityManager().find(Author.class,author.getId()));
        }
        Book book = new Book();
        book.setAuthors(bookAuthors);
        book.setTitle(tfTilte.getText());
        book.setQuantity(Integer.parseInt(tfQuantity.getText()));
        book.setCount(Integer.parseInt(tfQuantity.getText()));
        book.setPublishedYear(Integer.parseInt(tfPublishedYear.getText()));
        try {
            homeController.getApp().getEntityManager().getTransaction().begin();
            homeController.getApp().getEntityManager().persist(book);
            homeController.getApp().getEntityManager().getTransaction().commit();
            homeController.getLbInfo().setText("Книга успешно добавлена");
            tfTilte.setText("");
            tfQuantity.setText("");
            tfPublishedYear.setText("");
            lvAuthors.getItems().clear();
        } catch (Exception e) {
            homeController.getLbInfo().setText("Книгу добавить не удолось");
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAuthors.setCellFactory(new Callback<ListView<Author>,ListCell<Author>>(){
                @Override
                public ListCell<Author> call(ListView<Author> p) {
                    return new ListCell<Author>(){
                       @Override
                       protected void updateItem(Author author, boolean empty){
                           super.updateItem(author, empty);
                           if(author != null){
                               setText(author.getFistname() + " "+author.getLastname());
                           }else{
                               setText(null);
                           }
                       }
                    };
                }
            });
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
}
