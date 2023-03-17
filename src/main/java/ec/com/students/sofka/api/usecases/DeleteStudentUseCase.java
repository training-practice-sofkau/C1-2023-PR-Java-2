package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.DeleteStudent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements DeleteStudent {

    private final IStudentRepository studentRepository;
    @Override
    public Mono<Void> delete(String id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Student not found")))
                .flatMap(studentRepository::delete);
    }
}
