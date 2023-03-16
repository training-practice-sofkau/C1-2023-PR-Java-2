package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class SaveNewStudentUseCase implements Function<StudentDTO, Mono<StudentDTO>>{

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public SaveNewStudentUseCase(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<StudentDTO> apply(StudentDTO studentDTO) {
        return studentRepository.save(toEntity(studentDTO))
                .map(this::toDto);
    }

    private Student toEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }

    private StudentDTO toDto(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

}
