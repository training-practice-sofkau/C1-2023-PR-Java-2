package ec.com.students.sofka.api.service.impl;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.service.IStudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor

public class StudentServiceImpl implements IStudentService {

    @Autowired
    private final IStudentRepository studentRepository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public Flux<StudentDTO> getAllStudents() {
        return this.studentRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> getStudentById(String id) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> saveStudent(StudentDTO studentDTO) {
        return this.studentRepository.save(toEntity(studentDTO)).map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> updateStudent(String id, StudentDTO studentDTO) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(student -> {
                    studentDTO.setStudentID(student.getStudentID());
                    return this.saveStudent(studentDTO);
                });
    }

    @Override
    public Mono<Void> deleteStudent(String id) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")))
                .flatMap(this.studentRepository::delete)
                //.map(s -> "Student deleted");
                .onErrorResume(Mono::error);
    }

    @Override
    public StudentDTO toDto(Student student) {
        return this.mapper.map(student, StudentDTO.class);
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        return this.mapper.map(studentDTO, Student.class);
    }
}
