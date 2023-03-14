package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {

    //GETS

    Flux<StudentDTO> getAllStudents();

    Mono<StudentDTO> getStudentById(String id);

    //POST

    Mono<StudentDTO> saveStudent(StudentDTO bookDTO);

    //PUT

    Mono<StudentDTO> updateStudent(String id, StudentDTO bookDTO);

    //DELETE

    Mono<String> deleteStudent(String id);

    //Mappers

    StudentDTO toDto(Student book);

    Student toEntity(StudentDTO bookDTO);
}
