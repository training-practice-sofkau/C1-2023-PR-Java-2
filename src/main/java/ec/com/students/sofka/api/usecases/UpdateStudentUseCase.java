package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.IUpdateStudent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateStudentUseCase implements IUpdateStudent {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<StudentDTO> update(String id, StudentDTO studentDTO) {
        return studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return studentRepository.save(modelMapper.map(studentDTO, Student.class));
                })
                .map(student -> modelMapper.map(student, StudentDTO.class));
    }
}
