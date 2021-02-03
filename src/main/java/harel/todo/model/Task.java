package harel.todo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Task {

	@Setter
	String title;
	LocalDateTime createdDate;
	@Setter
	LocalDateTime modifiedDate;
	@Setter
	boolean isDone;
	
	public Task() {
		this.title = "";
		this.createdDate = LocalDateTime.now();
		this.isDone = false;
	}
	
	public Task(String title) {
		this.title = title;
		this.createdDate = LocalDateTime.now();
		this.isDone = false;
	}
	
	
}
