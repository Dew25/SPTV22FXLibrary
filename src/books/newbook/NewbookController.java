/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.newbook;

import authors.listauthors222.ListAuthorsController;
import entity.Author;
import entity.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
 * @author admin
 */
public class NewbookController implements Initializable {

    private HomeController homeController;
    private ObservableList<Author> authors;
    private VBox vbAuthorsRoot;
    @FXML private TextField tfTitle;
    @FXML private TextField tfPublishedYear;
    @FXML private TextField tfQuantity;
    @FXML private Button btAddAuthors;
    @FXML private ListView lvAuthors;
    @FXML private Button btAddBook;
    
    
    
    
    @FXML private void clickAddAuthors(){
        List<Author> listAuthors = getHomeController().getApp().getEntityManager()
                .createQuery("SELECT a FROM Author a")
                .getResultList();
        this.authors = FXCollections.observableArrayList(listAuthors);
        ListView<Author> listViewAuthorsWindow = new ListView<>(authors);
        listViewAuthorsWindow.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        listViewAuthorsWindow.setCellFactory((ListView<Author> authors) -> new ListCell<Author>() {
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
        Stage modalWindows = new Stage();
        // Кнопка для получения выбранных авторов
        Button selectButton = new Button("Выбрать");
        selectButton.setOnAction(event -> {
            ObservableList<Author> selectedAuthors = listViewAuthorsWindow.getSelectionModel().getSelectedItems();
            
            lvAuthors.getItems().addAll(selectedAuthors);
            modalWindows.close();
        });
        VBox root = new VBox(listViewAuthorsWindow, selectButton);
        Scene scene = new Scene(root,300, 250);
        modalWindows.setTitle("Список авторов");
        modalWindows.initModality(Modality.WINDOW_MODAL);
        modalWindows.initOwner(homeController.getApp().getPrimaryStage());
        modalWindows.setScene(scene);
        modalWindows.show();
    }
    @FXML private void clickAddBook(){
        Book book = new Book();
        book.setTitle(tfTitle.getText());
        book.setPublishedYear(Integer.parseInt(tfPublishedYear.getText()));
        book.setQuantity(Integer.parseInt(tfQuantity.getText()));
        book.setCount(book.getQuantity());
        List<Author>bookAuthors = new ArrayList<>();
        for (int i = 0; i < lvAuthors.getItems().size(); i++) {
            Author author = (Author) lvAuthors.getItems().get(i);
            bookAuthors.add(author);
        }
        book.getAuthors().addAll(bookAuthors);
        try {
            homeController.getApp().getEntityManager().getTransaction().begin();
                homeController.getApp().getEntityManager().persist(book);
            homeController.getApp().getEntityManager().getTransaction().commit();
            tfTitle.setText("");
            tfPublishedYear.setText("");
            tfQuantity.setText("");
            lvAuthors.getItems().clear();
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("info");
            homeController.getLbInfo().setText("Книга добавлена");
            
        } catch (Exception e) {
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("infoError");
            homeController.getLbInfo().setText("Книга добавить не удалось");
        }
    }
    
    @FXML private List<Author> getListAuthors(){
        
        return new ArrayList<>();
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

    private void createModalWindows() {
        
    }

    public HomeController getHomeController() {
        return homeController;
    }
    

    public ObservableList<Author> getAuthors() {
        return authors;
    }
    
}
