package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import ec.com.students.sofka.api.usecases.interfaces.IGetAllStudents;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetAllStudentsUseCase implements IGetAllStudents {

    private final IStudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<StudentDTO> getAll() {
        return studentRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(student -> modelMapper.map(student, StudentDTO.class));
    }
}
