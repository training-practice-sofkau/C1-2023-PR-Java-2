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
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;

    GetStudentByIdUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new GetStudentByIdUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getBookByID_Success")
    void getBookByID(){

        var student = new Student("testId", "56892345", "Pepito", "Perez", true, new ArrayList<>());


        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(student));

        var response = service.apply("testId");

        StepVerifier.create(response)
                .expectNext(modelMapper.map(student, StudentDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("testId");
    }

}