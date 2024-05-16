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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
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
    @FXML DatePicker dpFrom;
    @FXML DatePicker dpTo;
    @FXML Label lbRangeBooks;
    
    

    public RangepageController() {
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Создаем объект StringConverter для форматирования даты
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dpFrom.setConverter(converter);
        dpTo.setConverter(converter);
        
        
    }    

    public void setHomeController(HomeController homeController) {
       this.homeController = homeController;
    }
    
    @FXML
    public void changeDatePicker(){
        LocalDate localDateFrom = dpFrom.getValue();
        LocalDate localDateTo = dpTo.getValue().plusDays(1);
        LocalDate currentLocalDate = LocalDate.now();
        
        if(localDateFrom == null || localDateFrom.isAfter(currentLocalDate)
                || localDateFrom.isEqual(localDateTo) || localDateFrom.isAfter(localDateTo)){
            homeController.getLbInfo().setText("Дата выбрана неправильно");
            lvRangeBooks.setItems(null);
            return;
        }
        Date dateFrom = Date.from(localDateFrom.atStartOfDay(
                ZoneId.systemDefault()).toInstant());
        Date dateTo = Date.from(localDateTo.atStartOfDay(
                ZoneId.systemDefault()).toInstant());
        listHistoryes = homeController.getApp().getEntityManager()
                .createQuery(
                    "SELECT h FROM History h WHERE h.takeOutBook >= :dateFrom AND h.takeOutBook <= :dateTo"
                )
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();
        showRangeBooks();
        showRangeReaders();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        lbRangeBooks.setText(String.format("Рейтинг книг с %s до %s (исключительно)"
                ,sdf.format(dateFrom),sdf.format(dateTo)));
    }
    
    public void setListHistories(){
        listHistoryes = homeController.getApp().getEntityManager()
                .createQuery("SELECT h FROM History h")
                .getResultList();
    }
    public void showRangeBooks() {
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
        homeController.getLbInfo().setText("");
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
        tvRangeReaders.getColumns().clear();
        tvRangeReaders.getColumns().addAll(firstnameColumn,lastnameColumn,loginColumn,rangeColumn);
        
        tvRangeReaders.setItems(tableData);
        
    }
    
}
