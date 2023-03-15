package ec.com.students.sofka.api.repository;

import ec.com.students.sofka.api.domain.collection.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
