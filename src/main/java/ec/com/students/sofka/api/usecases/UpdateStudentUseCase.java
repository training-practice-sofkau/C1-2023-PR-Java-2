package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

public class UpdateStudentUseCase implements BiFunction<String, StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public UpdateStudentUseCase(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<StudentDTO> apply(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(student -> {
                    student.setActive(studentDTO.getActive());
                    student.setBooks(studentDTO.getBooks());
                    return student;
                })
                .flatMap(studentRepository::save).
                map(this::toDto);
    }

    private StudentDTO toDto(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

}
