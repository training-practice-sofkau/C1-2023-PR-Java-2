package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements Function<String, Mono<Void>> {

    private final IStudentRepository studentRepository;

    @Override
    public Mono<Void> apply(String id) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Student not found")))
                .flatMap(student -> this.studentRepository.deleteById(student.getId()))
                .onErrorResume(throwable -> Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));
    }
}
