package harel.todo.dto;

import java.util.List;

import harel.todo.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	String login;
	List<Task> tasks;

}
