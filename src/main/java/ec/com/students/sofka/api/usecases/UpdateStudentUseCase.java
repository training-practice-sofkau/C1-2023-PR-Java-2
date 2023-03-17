package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.IUpdateStudent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Validated
public class UpdateStudentUseCase implements IUpdateStudent {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<StudentDTO> update(String id, @Valid StudentDTO studentDTO) {
        return studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")))
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return studentRepository.save(modelMapper.map(studentDTO, Student.class));
                })
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .onErrorResume(error -> Mono.error(new RuntimeException("Student not found")));
    }
}
