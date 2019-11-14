package ru.habrahabr.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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

    @Column(unique = true)
    private StringProperty phone = new SimpleStringProperty();

    @Column(unique = true)
    private StringProperty email = new SimpleStringProperty();

    public TaskWithProperties() {
    }

    public TaskWithProperties(Long id, String name, String phone, String email) {
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
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

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
