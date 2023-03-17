package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.useCases.interfaces.SaveStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveStudentUseCase implements SaveStudent {
    private final IStudentRepository iStudentRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<StudentDTO> save(StudentDTO studentDTO) {
        return this.iStudentRepository.save(mapper.map(studentDTO, Student.class))
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
