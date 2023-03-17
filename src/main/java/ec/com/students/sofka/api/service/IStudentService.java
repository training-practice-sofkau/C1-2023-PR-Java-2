package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {

    // Getters

    Flux<StudentDTO> getAllStudents();

    Mono<StudentDTO> getStudentById(String id);

    // Post
    Mono<StudentDTO> saveStudent(StudentDTO studentDTO);

    // Put
    Mono<StudentDTO> updateStudent(String id, StudentDTO studentDTO);

    // Delete
    Mono<String> deleteStudent(String id);

    // Mapping
    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);


}
