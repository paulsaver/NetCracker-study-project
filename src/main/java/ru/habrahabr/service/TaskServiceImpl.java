package ru.habrahabr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.habrahabr.entity.Task;
import ru.habrahabr.repository.TaskRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Date: 27.08.15
 * Time: 17:23
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 * @author http://mruslan.com
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод добавляет парочку записей в БД после запуска приложения,
     * чтобы не было совсем пусто.
     *
     * Из-за того, что подключена H2 (in-memory) БД.
     */
    @PostConstruct
    public void generateTestData() {
        save(new Task("Иван Иванов", "+123456789", "ivan@ivan.ov"));
        save(new Task("Петр Петров", "+987654321", "petr@pe.tr"));
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }
}
