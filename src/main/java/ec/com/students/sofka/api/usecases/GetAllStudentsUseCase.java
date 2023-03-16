package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class GetAllStudentsUseCase implements Supplier<Flux<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public GetAllStudentsUseCase(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Flux<StudentDTO> get() {
        return studentRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    private StudentDTO toDto(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

}
