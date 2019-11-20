package ru.habrahabr.service;

import ru.habrahabr.entity.Task;

import java.util.List;

/**
 * Date: 27.08.15
 * Time: 17:22
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 * @author http://mruslan.com
 */
public interface TaskService {

    Task save(Task task);

    List<Task> findAll();

    void deleteById(Long id);
}
