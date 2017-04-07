package me.jarad.capella.persistance.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import me.jarad.capella.persistance.Model;

public interface ModelMongoRepository extends PagingAndSortingRepository<Model, String> {
}