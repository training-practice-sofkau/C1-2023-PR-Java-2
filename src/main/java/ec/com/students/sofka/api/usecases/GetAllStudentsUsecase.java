package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllStudentsUsecase implements Supplier<Flux<StudentDTO>> {

    private final IStudentRepository iStudentRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<StudentDTO> get() {
        return this.iStudentRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
