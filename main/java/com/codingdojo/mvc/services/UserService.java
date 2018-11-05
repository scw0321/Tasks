package com.codingdojo.mvc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.codingdojo.mvc.models.Task;
import com.codingdojo.mvc.models.User;
import com.codingdojo.mvc.repositories.UserRepository;
import com.codingdojo.mvc.repositories.TaskRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }

	public List<Task> allTasks() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}

	public Task createTask(Task t) {
        return taskRepository.save(t);
    }

	public List<User> allUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public Task findTask(Long id) {
		Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            return null;
        }
	}

	public void deleteTask(Long id) {
		// TODO Auto-generated method stub
		Task t = this.findTask(id);
		if(t!=null) {
			taskRepository.deleteById(id);			
		}		
		
	}

//	public Task updateTask(@Valid Task task) {
//		// TODO Auto-generated method stub
//		return taskRepository.save(task);
//	}
//	public Task updateTask (Long id, String task_name, String creator, String assignee, String priority) {
//		// TODO Auto-generated method stub
//		
//		Task t = this.findTask(id);		
//		if (t!=null) {
//			t.setTask_name(task_name);
//			t.setAssignee(assignee);
//			t.setPriority(priority);
//			
//		}
//		return taskRepository.save(t);
//	}

	public Task updateTask(@Valid Task task) {
		// TODO Auto-generated method stub
		return taskRepository.save(task);
	}
	
}