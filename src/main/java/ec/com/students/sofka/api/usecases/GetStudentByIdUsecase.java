package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class GetStudentByIdUsecase implements Function<String, Mono<StudentDTO>> {
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;


    @Override
    public Mono<StudentDTO> apply(String id) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
