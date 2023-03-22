package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.usecases.impl.CreateStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CreateStudentUsecase implements CreateStudent {
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;
    @Override
    public Mono<StudentDTO> create(StudentDTO studentDTO) {
        return this.studentRepository
                .save(mapper.map(studentDTO, Student.class))
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
