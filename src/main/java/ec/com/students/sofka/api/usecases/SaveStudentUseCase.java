package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveStudentUseCase implements Function<StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(StudentDTO studentDTO) {
        return null;
    }
}
