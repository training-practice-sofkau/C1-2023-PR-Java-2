package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements Function<String, Mono<String>> {

    private final IStudentRepository studentRepository;


    @Override
    public Mono<String> apply(String id) {
        return studentRepository.findById(id)
                .flatMap(student -> studentRepository.delete(student).thenReturn(id))
                .switchIfEmpty(Mono.error(new RuntimeException(id)));
    }
}
