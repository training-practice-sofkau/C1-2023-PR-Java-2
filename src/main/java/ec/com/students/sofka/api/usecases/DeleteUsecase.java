package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteUsecase implements Function<String, Mono<String>> {

    private final IStudentRepository iStudentRepository;

    @Override
    public Mono<String> apply(String id) {
        return this.iStudentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")))
                .flatMap(student -> iStudentRepository.delete(student).thenReturn(id))
                .onErrorResume(Mono::error);
    }

}
