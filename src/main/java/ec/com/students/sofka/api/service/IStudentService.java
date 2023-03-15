package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {

    Flux<StudentDTO> findAll();

    Mono<StudentDTO> findById(String id);

    Mono<StudentDTO> save(StudentDTO studentDTO);

    Mono<StudentDTO> update(String id, StudentDTO studentDTO);

    Mono<Void> delete(String id);

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);

}
