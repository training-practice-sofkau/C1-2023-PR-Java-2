package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UpdateStudentUseCase implements BiFunction<String, StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(String ID, StudentDTO studentDTO) {
        return this.studentRepository
                .findById(ID)
                .switchIfEmpty(Mono.empty())
                .map(student ->{
                    studentDTO.setId(student.getId());
                    return mapper.map(
                            studentRepository
                                    .save(mapper.map(studentDTO, Student.class)),
                            StudentDTO.class);
                });
    }
}
