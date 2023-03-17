package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveStudentUseCase implements Function<StudentDTO, Mono<StudentDTO>> {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<StudentDTO> apply(StudentDTO studentDTO) {
        return this.studentRepository.save(modelMapper.map(studentDTO, Student.class))
                .switchIfEmpty(Mono.empty())
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .onErrorResume(throwable -> Mono.error(throwable));
    }
}
