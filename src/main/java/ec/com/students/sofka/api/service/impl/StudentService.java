package ec.com.students.sofka.api.service.impl;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.service.IStudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService implements IStudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    public Flux<StudentDTO> findAll() {
        return studentRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> findById(String id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> save(StudentDTO studentDTO) {
        return studentRepository.save(toEntity(studentDTO))
                .map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> update(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(student -> {
                    student.setActive(studentDTO.getActive());
                    student.setBooks(studentDTO.getBooks());
                    return student;
                })
                .flatMap(studentRepository::save)
                .map(this::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Student not found")))
                .flatMap(studentRepository::delete)
                .onErrorResume(error -> Mono.error(new Exception("Error deleting student, not found")));
    }

    @Override
    public StudentDTO toDto(Student student) {
        return new ModelMapper().map(student, StudentDTO.class);
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        return new ModelMapper().map(studentDTO, Student.class);
    }
}
