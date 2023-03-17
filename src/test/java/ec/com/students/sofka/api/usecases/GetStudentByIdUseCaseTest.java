package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(repository,mapper);
    }

    @Test
    void getStudentById(){

        var student = Mono.just(new Student(
                "123456",
                "Peter",
                "Parker"
        ));

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(student);

        var response = getStudentByIdUseCase.apply("");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findById("");

    }

}