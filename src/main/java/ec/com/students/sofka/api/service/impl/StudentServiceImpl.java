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

    private final IStudentRepository studentRepository;
    private final ModelMapper mapper;


    @Override
    public Flux<StudentDTO> getAllStudents() {
        return this.studentRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDTO);
    }

    @Override
    public Mono<StudentDTO> getStudentById(String Id) {
        return this.studentRepository
                .findById(Id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDTO);
    }

    @Override
    public Mono<StudentDTO> saveStudent(StudentDTO studentDTO) {
        return this.studentRepository
                .save(toEntity(studentDTO))
                .map(this::toDTO);
    }

    @Override
    public Mono<StudentDTO> updateStudent(String Id, StudentDTO studentDTO) {
        return this.studentRepository
                .findById(Id)
                .switchIfEmpty(Mono.empty())
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return this.saveStudent(studentDTO);
                });
    }

    @Override
    public Mono<String> deleteStudent(String Id) {
        return this.studentRepository
                .findById(Id)
                .switchIfEmpty(Mono.empty())
                .flatMap(student -> this.studentRepository.deleteById(student.getId()))
                .flatMap(unused -> Mono.just(Id));
    }

    @Override
    public StudentDTO toDTO(Student student) {
        return this.mapper.map(student, StudentDTO.class);
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        return this.mapper.map(studentDTO, Student.class);
    }
}
