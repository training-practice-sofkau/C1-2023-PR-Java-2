package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.StudentRepository;
import reactor.core.publisher.Mono;
import java.util.function.Function;

public class DeleteStudentUseCase implements Function<String, Mono<Void>> {

    private final StudentRepository studentRepository;

    public DeleteStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        return  studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Student not found")))
                .flatMap(studentRepository::delete)
                .onErrorResume(error -> Mono.error(new Exception("Error deleting student, not found")));
    }

}
