package ec.com.students.sofka.api.usecases.impl;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CreateStudent {
    Mono<StudentDTO> create(StudentDTO studentDTO);
}
