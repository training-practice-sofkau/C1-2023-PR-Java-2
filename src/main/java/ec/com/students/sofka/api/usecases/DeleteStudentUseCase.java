package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.IDeleteStudent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteStudentUseCase implements IDeleteStudent {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<Void> delete(String id) {
        return studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")))
                .flatMap(student -> studentRepository.deleteById(student.getId()));
    }
}
