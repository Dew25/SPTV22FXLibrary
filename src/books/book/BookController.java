/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;


import books.listbooks.ListbooksController;
import entity.Author;
import entity.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


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

    public void setListBooks(ListbooksController listbooksController) {
        this.listbooksController = listbooksController;
    }

    public void setBook(Book book) {
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

   
    
}
