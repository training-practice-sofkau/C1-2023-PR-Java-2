package ec.com.students.sofka.api.usecases.interfaces;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Mono;

public interface IUpdateStudent {

    Mono<StudentDTO> update(String id, StudentDTO studentDTO);
}
