/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.listbooks;

import books.book.BookController;
import entity.Book;
import entity.History;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ListbooksController implements Initializable {

    private HomeController homeContoller;
    @FXML private HBox hbListBooksContent;
    private Stage modalWindow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setHomeController(HomeController homeController) {
        this.homeContoller = homeController;
    }
    
    public void loadBooks() {
        List<Book> listBooks = homeContoller.getApp().getEntityManager()
                .createQuery("SELECT b FROM Book b")
                .getResultList();
        try {
            hbListBooksContent.getChildren().clear();
            for (int i = 0; i < listBooks.size(); i++) {
                FXMLLoader bookLoader = new FXMLLoader();
                bookLoader.setLocation(getClass().getResource("/books/book/book.fxml"));
                VBox vbBookRoot = bookLoader.load();
                Book book = listBooks.get(i);
                BookController bookController = bookLoader.getController();
                bookController.setListBooks(this);
                bookController.setBook(book);
                // Навешиваем обработчик события наведения мыши на VBox
                vbBookRoot.setOnMouseEntered(event -> {
                    vbBookRoot.setCursor(Cursor.HAND); // Устанавливаем курсор на руку при наведении
                });

                // Навешиваем обработчик события ухода мыши с VBox
                vbBookRoot.setOnMouseExited(event -> {
                    vbBookRoot.setCursor(Cursor.DEFAULT); // Возвращаем стандартный курсор при уходе
                });
                vbBookRoot.setOnMouseClicked(event -> {
                    System.out.println("Вы нажали на книгу: "+book.getTitle());
                    showBook(book);
                });
                hbListBooksContent.getChildren().add(vbBookRoot);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ListbooksController.class.getName()).log(Level.SEVERE, "not found book.fxml", ex);
        
            
        }
    }

    public HomeController getHomeContoller() {
        return homeContoller;
    }

    private void showBook(Book book) {
        try {
            modalWindow = new Stage();
            FXMLLoader bookLoader = new FXMLLoader();
            bookLoader.setLocation(getClass().getResource("/books/book/book.fxml"));
            VBox vbBookRoot = bookLoader.load();
            Button btTakeOn=new Button("Читать книгу");
            btTakeOn.setOnAction(event->{
                takeOnBookToReader(book);
            });
            vbBookRoot.getChildren().add(btTakeOn);
            Scene scene = new Scene(vbBookRoot,300, 250);
            modalWindow.setTitle(book.getTitle());
            modalWindow.initModality(Modality.WINDOW_MODAL);
            modalWindow.initOwner(getHomeContoller().getApp().getPrimaryStage());
            modalWindow.setScene(scene);
            modalWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ListbooksController.class.getName()).log(Level.SEVERE, "not found book.fxml", ex);
        }
    }

    private void takeOnBookToReader(Book book) {
        if(!(book.getCount() > 0)){
            getHomeContoller().getLbInfo().setText("Нет книги в наличии!");
            modalWindow.close();
            return;
        }
        History history = new History();
        history.setBook(book);
        if(sptv22fxlibrary.SPTV22FXLibrary.user==null){
            getHomeContoller().getLbInfo().setText("Войдите в систему");
            getHomeContoller().login();
            modalWindow.close();
            return;
        }
        history.setUser(sptv22fxlibrary.SPTV22FXLibrary.user);
        history.setTakeOutBook(new GregorianCalendar().getTime());
        book.setCount(book.getCount()-1);
        getHomeContoller().getApp().getEntityManager().getTransaction().begin();
        getHomeContoller().getApp().getEntityManager().merge(book);
        getHomeContoller().getApp().getEntityManager().persist(history);
        getHomeContoller().getApp().getEntityManager().getTransaction().commit();
        getHomeContoller().getLbInfo()
                .setText("Книга "+book.getTitle()
                        +" выдана читателю: "+history.getUser().getReader().getFirstname()
                        +" "+history.getUser().getReader().getLastname());
        modalWindow.close();
    }
}
