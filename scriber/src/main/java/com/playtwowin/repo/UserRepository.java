package com.playtwowin.repo;

import org.springframework.data.repository.CrudRepository;

import com.playtwowin.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}
