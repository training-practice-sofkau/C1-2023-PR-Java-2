package ec.com.students.sofka.api.repository;

import ec.com.students.sofka.api.domain.collection.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

}
