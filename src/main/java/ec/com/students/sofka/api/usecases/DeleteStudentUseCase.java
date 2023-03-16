package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements Function<String, Mono<Void>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;


    @Override
    public Mono<Void> apply(String ID) {
        return this.studentRepository
                .findById(ID)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(student -> this.studentRepository.deleteById(student.getId()))
                .onErrorResume(throwable -> Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));
    }


}
