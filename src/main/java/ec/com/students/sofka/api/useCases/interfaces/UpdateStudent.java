package ec.com.students.sofka.api.useCases.interfaces;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateStudent {
    Mono<StudentDTO> update(String id, StudentDTO studentDTO);
}
