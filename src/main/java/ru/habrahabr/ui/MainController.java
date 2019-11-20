package ru.habrahabr.ui;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.habrahabr.DateUtil;
import ru.habrahabr.entity.Task;
import ru.habrahabr.service.TaskService;

import javax.annotation.PostConstruct;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Date: 27.08.15
 * Time: 11:10
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 * @author http://mruslan.com
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
    @Autowired private TaskService taskService;

    // Инъекции JavaFX
    @FXML private TableView<Task> table;
    @FXML private TextField txtName;
    @FXML private TextField txtDesc;
    @FXML private TextField txtContacts;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;

    // Variables
    private ObservableList<Task> data;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     *
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     *
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct,
     * который вызовется спрингом, после того, как им будут произведены все инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
        datePicker.setValue(DateUtil.getCurrentDate());
        timePicker.setValue(LocalTime.now().plusHours(1));
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<Task> tasks = taskService.findAll();
        data = FXCollections.observableArrayList(tasks);

        // Столбцы таблицы
        TableColumn<Task, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Task, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));

        TableColumn<Task, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Task, String> contactsColumn = new TableColumn<>("Contacts");
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));

        table.getColumns().setAll(idColumn, nameColumn, descColumn, dateColumn, contactsColumn);

        // Данные таблицы
        table.setItems(data);

        table.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                VBox dialogVbox = new VBox(20);
                dialogVbox.setAlignment(Pos.TOP_LEFT);

                HBox nameLine = new HBox(20);
                nameLine.setAlignment(Pos.CENTER);
                nameLine.getChildren().add(new Text("Name"));
                nameLine.getChildren().add(new TextField(table.getSelectionModel().getSelectedItem().getName()));

                dialogVbox.getChildren().add(nameLine);
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }});
    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    @FXML
    public void addContact() {
        String name = txtName.getText();
        String desc = txtDesc.getText();
        String contacts = txtContacts.getText();
        String date = datePicker.getValue().toString();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(desc) || StringUtils.isEmpty(contacts) || StringUtils.isEmpty(date)) {
            return;
        }

        Task task = new Task(name, desc, LocalDateTime.of(datePicker.getValue(), timePicker.getValue()), contacts);
        taskService.save(task);
        data.add(task);

        // чистим поля
        txtName.setText("");
        txtDesc.setText("");
        txtContacts.setText("");
        datePicker.setValue(DateUtil.getCurrentDate());
    }

    @FXML
    public void deleteContact() {
        Task toDelete = table.getSelectionModel().getSelectedItem();

        table.getItems().remove(toDelete);

        taskService.deleteById(toDelete.getId());

    }

    @FXML
    public void editPopUp(MouseEvent event) {
        if (event.getClickCount() == 2) {
            System.out.println(table.getSelectionModel().getSelectedItem());
        }
    }
}
