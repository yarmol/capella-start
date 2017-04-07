package me.jarad.capella.persistance;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModelMongoRepository extends PagingAndSortingRepository<Model, String> {
}