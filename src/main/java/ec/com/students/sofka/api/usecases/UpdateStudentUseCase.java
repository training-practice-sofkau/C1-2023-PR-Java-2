package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.SaveStudent;
import ec.com.students.sofka.api.usecases.interfaces.UpdateStudent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
//@Validated
public class UpdateStudentUseCase implements UpdateStudent {
    private final IStudentRepository repository;

    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> update(String id, StudentDTO studentDTO) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                // It can not be empty because pass directly to the 200 httpstatus.
                //.switchIfEmpty(Mono.empty())
                .flatMap(student -> {
                    studentDTO.setId(student.getId());
                    return repository.save(mapper.map(studentDTO, Student.class));
                })
                .map(student -> mapper.map(student, StudentDTO.class));
                //.onErrorResume(error -> Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));
    }
}
