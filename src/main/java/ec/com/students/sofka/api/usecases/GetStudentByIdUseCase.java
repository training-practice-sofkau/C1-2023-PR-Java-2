package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.IGetStudentById;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetStudentByIdUseCase implements IGetStudentById {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public Mono<StudentDTO> getStudentById(String id) {
        return studentRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(student -> modelMapper.map(student, StudentDTO.class));
    }
}
