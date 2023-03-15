package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {
    //POST
    Mono<StudentDTO> saveBook(StudentDTO studentDTODTO);

    //GETS
    Flux<StudentDTO> getAllStudents();

    StudentDTO toDto(Student student);
    Student toEntity(StudentDTO studentDTO);
}
