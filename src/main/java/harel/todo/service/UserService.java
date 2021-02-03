package harel.todo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import harel.todo.dto.NewTaskDto;
import harel.todo.dto.NewUserDto;
import harel.todo.dto.UpdateTaskDto;
import harel.todo.dto.UserDto;
import harel.todo.exceptions.EntityNotFoundException;
import harel.todo.exceptions.UserExistsException;
import harel.todo.model.Task;
import harel.todo.model.User;
import harel.todo.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository repository;

	@Override
	public UserDto addUser(NewUserDto newUserDto) {
		if (repository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException(newUserDto.getLogin());
		}
		String password = BCrypt.hashpw(newUserDto.getPassword(), BCrypt.gensalt());
		User user = repository.save(new User(newUserDto.getLogin(), password));
		return new UserDto(user.getLogin(), user.getTasks());
	}

	@Override
	public UserDto getUser(String login) {
		User user = repository.findById(login).orElseThrow(() -> new EntityNotFoundException());
		return new UserDto(user.getLogin(), user.getTasks());
	}

	@Override
	public Task addNewTask(String login, NewTaskDto newTaskDto) {
		User user = repository.findById(login).orElseThrow(() -> new EntityNotFoundException());
		Task task = new Task(newTaskDto.getTitle()); 
		user.getTasks().add(task);
		repository.save(user);
		return task;
		
	}

	@Override
	public boolean deleteTask(String login, String createdDate) {
		User user = repository.findById(login).orElseThrow(() -> new EntityNotFoundException());
		if (user.removeTask(LocalDateTime.parse(createdDate))) {
			repository.save(user);
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public Task updateTask(String login, String createdDate, UpdateTaskDto updateTask) {
		User user = repository.findById(login).orElseThrow(() -> new EntityNotFoundException());
		user.getTasks().stream()
			.forEach(task -> {
				if (task.getCreatedDate().equals(LocalDateTime.parse(createdDate))) {
					task.setTitle(updateTask.getTitle());
					task.setDone(updateTask.isDone());
					task.setModifiedDate(LocalDateTime.now());
				}
			});
		repository.save(user);
		return user.getTaskByCreatedDate(LocalDateTime.parse(createdDate));
	}

	@Override
	public List<Task> getAllTasks(String login) {
		User user = repository.findById(login).orElseThrow(() -> new EntityNotFoundException());
		return user.getTasks();
	}

}
