package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DeleteStudentByIdUseCase implements Function<String, Mono<Void>> {

    private final IStudentRepository repository;

    private final ModelMapper mapper;


    @Override
    public Mono<Void> apply(String id) {
        return this.repository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(student -> this.repository.deleteById(student.getId()));
    }
}
