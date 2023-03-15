package ec.com.students.sofka.api.service.impl;

import ec.com.students.sofka.api.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl {

    private final IStudentRepository studentRepository;
    private final ModelMapper mapper;


}
