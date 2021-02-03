package harel.todo.security.service;

import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import harel.todo.dto.NewUserDto;
import harel.todo.exceptions.TokenValidateException;
import harel.todo.exceptions.UnauthorizedException;
import harel.todo.exceptions.UserNotFoundException;
import harel.todo.model.User;
import harel.todo.repository.IUserRepository;


@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	IUserRepository repository;

	@Override
	public String getLogin(String token) {
		NewUserDto newUserDto = tokenDecode(token);
		User user = repository.findById(newUserDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(newUserDto.getLogin()));
		if (!BCrypt.checkpw(newUserDto.getPassword(), user.getPassword())) {
			throw new UnauthorizedException();
		}	
		return user.getLogin();
	}

	private NewUserDto tokenDecode(String token) {
		try {
			String[] credentials = token.split(" ");
			String credential = new String(Base64.getDecoder().decode(credentials[1]));
			credentials = credential.split(":");
			return new NewUserDto(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new TokenValidateException();
		}
	}

}
