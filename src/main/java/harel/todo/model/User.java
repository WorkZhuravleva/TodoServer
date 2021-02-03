package harel.todo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import harel.todo.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode(of = "login")
@Getter
@Document(collection = "dbuser")
public class User {
	@Id
	String login;
	@Setter
	String password;
	List<Task> tasks;
	
	public User() {
		this.tasks = new ArrayList<>();
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.tasks = new ArrayList<>();
	}
	
	public boolean addTask(Task task) {
		return tasks.add(task);
	}
	
	public boolean removeTask(LocalDateTime createdDate) {
		return tasks.removeIf(t -> t.createdDate.equals(createdDate));
	}
	
	public Task getTaskByCreatedDate(LocalDateTime createdDate) {
		Task task = tasks.stream()
						.filter(t -> t.createdDate.equals(createdDate))
						.findFirst().orElseThrow(() -> new EntityNotFoundException());
		return task;
	}
}
