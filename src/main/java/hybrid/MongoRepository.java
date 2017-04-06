package hybrid;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepository extends PagingAndSortingRepository<Model, String> {
}