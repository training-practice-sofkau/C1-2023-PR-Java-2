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
    public Mono<StudentDTO> saveStudent(StudentDTO bookDTO) {
        return this.studentRepository.save(toEntity(bookDTO)).map(this::toDto);
    }

    @Override
    public Mono<StudentDTO> updateStudent(String id, StudentDTO bookDTO) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> {
                    bookDTO.setId(book.getStudentID());
                    return this.saveStudent(bookDTO);
                });
    }

    @Override
    public Mono<String> deleteStudent(String id) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> this.studentRepository.deleteById(book.getStudentID()))
                .flatMap(unused -> Mono.just(id));
    }

    @Override
    public StudentDTO toDto(Student book) {
        return this.mapper.map(book, StudentDTO.class);
    }

    @Override
    public Student toEntity(StudentDTO bookDTO) {
        return this.mapper.map(bookDTO, Student.class);
    }
}
