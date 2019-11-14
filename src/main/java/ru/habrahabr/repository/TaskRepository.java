package ru.habrahabr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.habrahabr.entity.Task;

import java.util.List;

/**
 * Date: 27.08.15
 * Time: 17:21
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 * @author http://mruslan.com
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAll();

}
