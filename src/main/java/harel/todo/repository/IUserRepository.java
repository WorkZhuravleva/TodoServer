package harel.todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import harel.todo.model.User;

public interface IUserRepository extends MongoRepository<User, String> {

}
