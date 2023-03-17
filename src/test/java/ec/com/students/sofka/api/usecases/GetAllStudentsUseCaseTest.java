package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(repository,mapper);
    }

    @Test
    void getAllStudent(){

        var student1 = new Student(
                "123456",
                "Peter",
                "Parker"
        );

        var student2 = new Student(
                "155948",
                "Donald",
                "Duck"
        );

        var fluxStudents = Flux.just(student1, student2);

        Mockito.when(repository.findAll()).thenReturn(fluxStudents);

        var response = getAllStudentsUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).findAll();

    }

}