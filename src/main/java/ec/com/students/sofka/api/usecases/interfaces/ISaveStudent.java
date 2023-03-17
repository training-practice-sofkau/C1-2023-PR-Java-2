package ec.com.students.sofka.api.usecases.interfaces;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ISaveStudent {

    Mono<StudentDTO> save(@Valid StudentDTO studentDTO);
}
