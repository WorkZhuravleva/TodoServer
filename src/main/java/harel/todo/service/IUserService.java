package harel.todo.service;

import java.util.List;

import harel.todo.dto.NewTaskDto;
import harel.todo.dto.NewUserDto;
import harel.todo.dto.UpdateTaskDto;
import harel.todo.dto.UserDto;
import harel.todo.model.Task;

public interface IUserService {
	
	UserDto addUser(NewUserDto newUserDto);
	
	UserDto getUser(String login);
	
	Task addNewTask(String login, NewTaskDto newTaskDto);
	
	boolean deleteTask(String login, String createdDate);
	
	Task updateTask(String login, String createdDate, UpdateTaskDto updateTask);
	
	List<Task> getAllTasks(String login);

}
