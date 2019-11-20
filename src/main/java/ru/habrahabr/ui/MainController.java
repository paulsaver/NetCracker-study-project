package ru.habrahabr.ui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.habrahabr.DateUtil;
import ru.habrahabr.entity.Task;
import ru.habrahabr.service.TaskService;

import javax.annotation.PostConstruct;
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
}
