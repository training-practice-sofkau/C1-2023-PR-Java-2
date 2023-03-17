package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.UpdateStudent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateStudentUsecase implements UpdateStudent {

    private final IStudentRepository iStudentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> update(String studentId, StudentDTO studentDTO) {
        return this.iStudentRepository.findById(studentId)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> {
                    studentDTO.setStudentID(studentId);
                    return iStudentRepository.save(mapper.map(studentDTO, Student.class));
                })
                .switchIfEmpty(Mono.empty())
                .map(book -> mapper.map(book, StudentDTO.class))
                .map(savedBook -> mapper.map(savedBook, StudentDTO.class))
                .onErrorResume(Mono::error);
    }

}
