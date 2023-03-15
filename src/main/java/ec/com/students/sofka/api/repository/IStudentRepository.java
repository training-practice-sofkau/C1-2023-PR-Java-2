package ec.com.students.sofka.api.repository;

import ec.com.students.sofka.api.domain.collection.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IStudentRepository  extends ReactiveMongoRepository<Student, String> {
}
