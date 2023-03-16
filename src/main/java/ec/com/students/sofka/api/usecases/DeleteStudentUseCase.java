package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;


}
