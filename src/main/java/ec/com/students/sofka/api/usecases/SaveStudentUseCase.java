package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveStudentUseCase implements Function<StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(StudentDTO studentDTO) {
        studentDTO.setBlocked(false);
        return this.studentRepository
                .save(mapper.map(studentDTO, Student.class))
                .map(student -> mapper.map(student, StudentDTO.class))
                .onErrorResume(throwable -> Mono.error(throwable));
    }
}
