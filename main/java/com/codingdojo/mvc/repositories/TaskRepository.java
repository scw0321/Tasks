package com.codingdojo.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.codingdojo.mvc.models.Task;
import com.codingdojo.mvc.models.User;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	List<Task> findAll();

	List<Task> findByUser(User id);

}
