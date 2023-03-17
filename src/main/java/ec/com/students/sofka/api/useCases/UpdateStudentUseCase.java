package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.useCases.interfaces.UpdateStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateStudentUseCase implements UpdateStudent {
    private final IStudentRepository iStudentRepository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<StudentDTO> update(String id, StudentDTO studentDTO) {
        return this.iStudentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return iStudentRepository.save(modelMapper.map(studentDTO, Student.class));
                })
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .onErrorResume(error -> Mono.error(new RuntimeException("Student not found")));
    }
}
