/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;


import books.listbooks.ListbooksController;
import entity.Author;
import entity.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
*/

public class BookController implements Initializable {

    private ListbooksController listbooksController;
    @FXML private Label lbTitleBook;
    @FXML private Label lbAuthors;
    @FXML private Label lbPublishedYear;
    @FXML private Label lbQuantity;
    @FXML private Label lbCount;
    @FXML private ImageView ivCover;
    @FXML private VBox vbBookAtributes;

    public void setListBooksController(ListbooksController listbooksController) {
        this.listbooksController = listbooksController;
    }

    public void setBook(Book book) {
        ivCover.setImage(new Image(new ByteArrayInputStream(book.getCover())));
        lbTitleBook.setText(book.getTitle());
        
        for (int i = 0; i < book.getAuthors().size(); i++) {
            Author author = book.getAuthors().get(i);
            lbAuthors.setText(author.getFistname()+" "+author.getLastname());
        }
        lbPublishedYear.setText(((Integer)book.getPublishedYear()).toString());
        lbQuantity.setText(((Integer)book.getQuantity()).toString());
        lbCount.setText(((Integer)book.getCount()).toString());
        
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    public ImageView getIvCover() {
        return ivCover;
    }

    public Label getLbTitleBook() {
        return lbTitleBook;
    }

    public void setLbTitleBook(Label lbTitleBook) {
        this.lbTitleBook = lbTitleBook;
    }

    public Label getLbAuthors() {
        return lbAuthors;
    }

    public void setLbAuthors(Label lbAuthors) {
        this.lbAuthors = lbAuthors;
    }

    public Label getLbPublishedYear() {
        return lbPublishedYear;
    }

    public void setLbPublishedYear(Label lbPublishedYear) {
        this.lbPublishedYear = lbPublishedYear;
    }

    public Label getLbQuantity() {
        return lbQuantity;
    }

    public void setLbQuantity(Label lbQuantity) {
        this.lbQuantity = lbQuantity;
    }

    public Label getLbCount() {
        return lbCount;
    }

    public void setLbCount(Label lbCount) {
        this.lbCount = lbCount;
    }

    public VBox getVbBookAtributes() {
        return vbBookAtributes;
    }

    public void setVbBookAtributes(VBox vbBookAtributes) {
        this.vbBookAtributes = vbBookAtributes;
    }

   
    
}
