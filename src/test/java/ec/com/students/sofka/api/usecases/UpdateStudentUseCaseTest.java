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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;

    UpdateStudentUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new UpdateStudentUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent(){

        Student student = new Student("testId", "56892345", "Pepito", "Perez", true, new ArrayList<>());
        Student studentU = new Student("testId", "56892345", "Pepita", "Perez", true, new ArrayList<>());



        Mockito.when(repoMock.findById("testId")).thenReturn(Mono.just(student));
        Mockito.when(repoMock.save(Mockito.any())).thenReturn(Mono.just(studentU));

        var response = service.update("testId", modelMapper.map(studentU, StudentDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(studentU, StudentDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(studentU);
        Mockito.verify(repoMock).findById("testId");
    }

}