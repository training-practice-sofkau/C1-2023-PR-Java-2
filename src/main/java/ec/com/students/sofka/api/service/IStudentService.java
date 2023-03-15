package ec.com.students.sofka.api.service;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Mono;

public interface IStudentService {
    //POST
    Mono<StudentDTO> saveBook(StudentDTO studentDTODTO);
}
