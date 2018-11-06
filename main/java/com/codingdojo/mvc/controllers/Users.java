package com.codingdojo.mvc.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.codingdojo.mvc.models.Task;
import com.codingdojo.mvc.models.User;
import com.codingdojo.mvc.services.UserService;
import com.codingdojo.mvc.validator.UserValidator;

import java.util.List;

@Controller
public class Users {
    private final UserService userService;
    private final UserValidator userValidator;
    
    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    @RequestMapping("/")
    public String registerForm(@ModelAttribute("user") User user) {
        return "loginPage.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        // if result has errors, return the registration page
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    	userValidator.validate(user,result);
    	if(result.hasErrors()) {
    		return "loginPage.jsp";
    	}
    	else {
    		userService.registerUser(user);
    		session.setAttribute("userId", user.getId());
    		
    		return "redirect:/tasks";
    	}
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, User user) {
        // if the user is authenticated, save their user id in session
        // else, add error messages and return the login page
    	if(userService.authenticateUser(email, password)) {
    		User theuser = userService.findByEmail(email);
    		session.setAttribute("userId", theuser.getId());
    				
    		return "redirect:/tasks";
    	}
    	model.addAttribute("error", "Invalid login");
    	
    	return "loginPage.jsp";
    	
    }
    
    @RequestMapping("/tasks")
    public String home(HttpSession session, Model model) {
        // get user from session, save them in the model and return the home page
    	if(session.getAttribute("userId")==null) {
    		return "redirect:/registration";
    	}
    	Long id =(long)session.getAttribute("userId");
    	User loginUser = userService.findUserById(id);
    	model.addAttribute("user", loginUser); 
    	List<Task> task = userService.allTasks();
        model.addAttribute("task", task);
    	return "homePage.jsp";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
        // redirect to login page
    	session.invalidate();
    	return "redirect:/";
    }
    
    @RequestMapping("/tasks/new")
    public String newTask(@ModelAttribute("task") Task task, HttpSession session, Model model) {
    	Long id =(long)session.getAttribute("userId");
    	User loginUser = userService.findUserById(id);    	
    	task.setCreator(loginUser.getName());
    	List<User> user = userService.allUsers();
    	user.remove(loginUser);
		model.addAttribute("user", user);
		
        return "new.jsp";
    }
    @RequestMapping(value="/tasks/new", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute("task") Task task, BindingResult result, HttpSession session,  Model model) {    	
    	
    	if(result.hasErrors()) {     
    		Long id =(long)session.getAttribute("userId");
        	User loginUser = userService.findUserById(id);    	
        	task.setCreator(loginUser.getName());
        	List<User> user = userService.allUsers();
        	user.remove(loginUser);
    		model.addAttribute("user", user);
        	return "new.jsp";
    	}
    	
    	userService.createTask(task);
		return "redirect:/tasks";
    }
    @RequestMapping("/tasks/{id}")
    public String index(Model model,@PathVariable("id") Long id) {
  	Task task = userService.findTask(id);
      model.addAttribute("task", task);
      return "show.jsp";
    }
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE)
    public String destroy(@PathVariable("id") Long id) {
      userService.deleteTask(id);
      return "redirect:/tasks";
    }
    
    @RequestMapping("/tasks/{id}/edit")    
	public String edit(@PathVariable("id") Long id, HttpSession session, Task task, Model model) {
    	
    	Long theid =(long)session.getAttribute("userId");
    	User loginUser = userService.findUserById(theid);    	
    	
    	List<User> user = userService.allUsers();
    	user.remove(loginUser);
		model.addAttribute("user", user);
		
	    Task updateTask = userService.findTask(id);
	    model.addAttribute("task", updateTask);
	    return "edit.jsp";
	}
	        
	@RequestMapping(value="/tasks/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("task") Task task, BindingResult result) {
	    if (result.hasErrors()) {
	        return "edit.jsp";
	   } else {	        
			userService.updateTask(task);
	        return "redirect:/tasks";
	    }
	}
	
	
}
	
  
