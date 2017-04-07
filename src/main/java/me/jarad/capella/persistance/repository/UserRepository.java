package me.jarad.capella.persistance.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import me.jarad.capella.model.security.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}