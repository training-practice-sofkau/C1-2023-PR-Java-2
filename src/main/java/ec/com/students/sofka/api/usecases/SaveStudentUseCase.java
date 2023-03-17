package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.SaveStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveStudentUseCase implements SaveStudent {

    private final IStudentRepository studentRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<StudentDTO> save(StudentDTO studentDTO) {
        return studentRepository.save(mapper.map(studentDTO, Student.class))
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
