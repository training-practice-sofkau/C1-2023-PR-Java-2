package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetAllStudentsUseCase implements Supplier<Flux<StudentDTO>>{

    private final IStudentRepository studentRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<StudentDTO> get() {
        return this.studentRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
