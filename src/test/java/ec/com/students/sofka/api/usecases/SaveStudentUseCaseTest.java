package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;

    SaveStudentUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new SaveStudentUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("saveStudent_Success")
    void saveStudents() {
        //Build the scenario you need
        var student = new Student("testId", "56892345", "Pepito", "Perez", true, new ArrayList<>());

        Mockito.when(repoMock.save(student)).thenReturn(Mono.just(student));

        var response = service.save(modelMapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(student, StudentDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(student);

    }

}