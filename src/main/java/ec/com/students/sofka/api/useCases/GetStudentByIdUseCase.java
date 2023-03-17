package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetStudentByIdUseCase implements Function<String, Mono<StudentDTO>> {
    private final IStudentRepository iStudentRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<StudentDTO> apply(String id) {
        return this.iStudentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(student-> mapper.map(student, StudentDTO.class));
    }
}
