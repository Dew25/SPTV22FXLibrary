/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package range;

import entity.Book;
import entity.History;
import entity.User;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sptv22fxlibrary.HomeController;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class RangepageController implements Initializable {
    private List<History> listHistoryes;
    private HomeController homeController;
    @FXML ListView lvRangeBooks;
    @FXML TableView tvRangeReaders;

    public RangepageController() {
        
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

    public void showRangeBooks() {
        listHistoryes = homeController.getApp().getEntityManager()
                .createQuery("SELECT h FROM History h")
                .getResultList();
        Map<Book,Integer> mapRangeBook = new HashMap<>();
        for (History history : listHistoryes) {
            if(mapRangeBook.containsKey(history.getBook())){
                mapRangeBook.put(history.getBook(), mapRangeBook.get(history.getBook()) + 1);
            }else{
                mapRangeBook.put(history.getBook(), 1);
            }
        }
        Map<Book, Integer> sortedMapRatingBook = mapRangeBook.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));            
        //задача: отобразить отсортированный HashMap<Book,Integer> в ListView lvRangeBooks 
        lvRangeBooks.setItems(FXCollections.observableArrayList(sortedMapRatingBook.entrySet()));
        lvRangeBooks.setCellFactory(param -> new ListCell<Entry>(){
            @Override
            protected void updateItem(Entry entry,boolean empty){
                super.updateItem(entry, empty);
                if(entry==null || empty){
                    setText(null);
                }else{
                    setText(((Book)entry.getKey()).getTitle()
                                + " прочитана "
                                + entry.getValue()
                                + " раз(а)");
                }
            }
        });
    }

    public void showRangeReaders() {
        
        Map<User,Integer> mapRangeUser = new HashMap<>();
        for (History history : listHistoryes) {
            if(mapRangeUser.containsKey(history.getUser())){
                mapRangeUser.put(history.getUser(), mapRangeUser.get(history.getUser()) + 1);
            }else{
                mapRangeUser.put(history.getUser(), 1);
            }
        }
        Map<User, Integer> sortedMapRatingUser = mapRangeUser.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));   
        // Создаем ObservableList для TableView
        ObservableList<User> tableData = FXCollections.observableArrayList(sortedMapRatingUser.keySet());
       // Создаем колонки
        TableColumn<User, String> firstnameColumn = new TableColumn<>("Имя");
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("readerFirstname"));
        TableColumn<User, String> lastnameColumn = new TableColumn<>("Фамилия");
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("readerLastname"));
        TableColumn<User, String> loginColumn = new TableColumn<>("Логин");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<User, Integer> rangeColumn = new TableColumn<>("Прочитанных книг");
        rangeColumn.setCellValueFactory(user -> {
            int count = sortedMapRatingUser.get(user.getValue());
            return new javafx.beans.property.SimpleIntegerProperty(count).asObject();
        });
        
        tvRangeReaders.setItems(tableData);
        tvRangeReaders.getColumns().addAll(firstnameColumn,lastnameColumn,loginColumn,rangeColumn);
    }
    
}
