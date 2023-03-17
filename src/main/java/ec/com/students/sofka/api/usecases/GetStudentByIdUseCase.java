package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class GetStudentByIdUseCase implements Function<String, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public GetStudentByIdUseCase(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<StudentDTO> apply(String id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    private StudentDTO toDto(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

}
