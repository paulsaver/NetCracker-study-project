package ru.habrahabr.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс, аналогичный классу {@link Task} за тем исключением,
 * что поля из себя представляют JavaFX Property. Это может пригодиться.
 *
 * JPA с ним работает аналогично классу {@link Task}.
 */
//@Entity
//@Table
//@Access(AccessType.PROPERTY)
public class TaskWithProperties implements Serializable {

    private LongProperty id = new SimpleLongProperty();

    @Column
    private StringProperty name = new SimpleStringProperty();

    @Column
    private StringProperty desc = new SimpleStringProperty();

    @Column
    private StringProperty date = new SimpleStringProperty();

    @Column
    private StringProperty contacts = new SimpleStringProperty();

    public TaskWithProperties() {
    }

    public TaskWithProperties(Long id, String name, String desc, String date, String contacts) {
        setId(id);
        setName(name);
        setDesc(desc);
        setDate(date);
        setContacts(contacts);
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public String getContacts() {
        return contacts.get();
    }

    public StringProperty contactsProperty() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts.set(contacts);
    }
}
