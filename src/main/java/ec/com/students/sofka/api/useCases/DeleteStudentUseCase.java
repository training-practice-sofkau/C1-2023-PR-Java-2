package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.useCases.interfaces.DeleteStudent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements DeleteStudent {
    private final IStudentRepository iStudentRepository;

    @Override
    public Mono<Void> delete(String id) {
        return iStudentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Student not found")))
                .flatMap(iStudentRepository::delete);
    }
}
