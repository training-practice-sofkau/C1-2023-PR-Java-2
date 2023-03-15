package ec.com.students.sofka.api.service.impl;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.service.IStudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository iStudentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<StudentDTO> getAllStudent() {
        return iStudentRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> getStudentById(String id) {
        return iStudentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> saveStudent(StudentDTO studentDTO) {
        return iStudentRepository.save(toEntity(studentDTO)).map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> updateStudent(String id, StudentDTO studentDTO) {
        return iStudentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(
                        student -> {
                            studentDTO.setId(student.getId());
                            return this.saveStudent(studentDTO);
                        }
                );
    }

    @Override
    public Mono<String> deleteStudent(String id) {
        return iStudentRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Student not found")))
                .flatMap(st -> iStudentRepository.deleteById(id))
                .flatMap(s -> Mono.just(id));
    }

    @Override
    public StudentDTO toDto(Student student) {
        return this.modelMapper.map(student,StudentDTO.class);
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        return this.modelMapper.map(studentDTO,Student.class);
    }
}
