package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UpdateStudentUseCase implements BiFunction<String, StudentDTO, Mono<StudentDTO>> {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<StudentDTO> apply(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Not found")))
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return studentRepository.save(modelMapper.map(studentDTO, Student.class));
                }).map(student -> modelMapper.map(student, StudentDTO.class))
                .onErrorResume(throwable -> Mono.error(throwable));
    }
}
