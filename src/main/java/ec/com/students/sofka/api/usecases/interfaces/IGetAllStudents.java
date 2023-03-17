package ec.com.students.sofka.api.usecases.interfaces;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface IGetAllStudents {

    Flux<StudentDTO> getAll();
}
