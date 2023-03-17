package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.ISaveStudent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Validated
public class SaveStudentUseCase implements ISaveStudent {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public Mono<StudentDTO> save(@Valid StudentDTO studentDTO) {
        return studentRepository
                .save(modelMapper.map(studentDTO, Student.class))
                .map(student -> modelMapper.map(student, StudentDTO.class));
    }
}
