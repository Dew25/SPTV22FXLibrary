/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import sptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author user
 */
public class BookController implements Initializable {

    private HomeController homeController;

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
