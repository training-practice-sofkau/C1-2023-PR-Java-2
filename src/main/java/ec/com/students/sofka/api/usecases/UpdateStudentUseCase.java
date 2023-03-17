package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.UpdateStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateStudentUseCase implements UpdateStudent {

    private final IStudentRepository studentRepository;

    private final ModelMapper mapper;



    @Override
    public Mono<StudentDTO> update(String id, StudentDTO studentDTO) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return studentRepository.save(mapper.map(studentDTO, Student.class));
                })
                .map(student -> mapper.map(student, StudentDTO.class));
    }
}
