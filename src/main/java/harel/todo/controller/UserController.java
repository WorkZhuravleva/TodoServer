package harel.todo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import harel.todo.dto.NewTaskDto;
import harel.todo.dto.NewUserDto;
import harel.todo.dto.UpdateTaskDto;
import harel.todo.dto.UserDto;
import harel.todo.model.Task;
import harel.todo.service.IUserService;

@RestController
@RequestMapping("/todo")
public class UserController {
	
	@Autowired
	IUserService service;
	
	@PostMapping("/registration")
	public UserDto register(@RequestBody NewUserDto newUserDto) {
		return service.addUser(newUserDto);
	}
	
	@PostMapping("/user/login")
	public UserDto login(Principal principal) {
		return service.getUser(principal.getName());
	}
	
	@PostMapping("/user/{login}/task")
	public Task addNewTask(@PathVariable String login, @RequestBody NewTaskDto newTaskDto) {
		return service.addNewTask(login, newTaskDto);
	}
	
	@DeleteMapping("/user/{login}/task/{createdDate}")
	public boolean deleteTask(@PathVariable String login, @PathVariable String createdDate) {
		return service.deleteTask(login, createdDate);
	}
	
	@PutMapping("/user/{login}/task/{createdDate}")
	public Task updateTask(@PathVariable String login, @PathVariable String createdDate, @RequestBody UpdateTaskDto updateTask) {
		return service.updateTask(login, createdDate, updateTask);
	}
	
	@GetMapping("/user/{login}/tasks")
	public List<Task> getTasks(@PathVariable String login){
		return service.getAllTasks(login);
	}

}
