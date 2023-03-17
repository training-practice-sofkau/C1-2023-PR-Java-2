package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    GetAllStudentsUseCase getAllStudentsUseCase;


    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(repository, mapper);
    }

    @Test
    @DisplayName("getAllStudent_Success")
    void getAllStudents(){
        var fluxStudents = Flux.just(new Student("87483", "Pedro", "Infante"),
                new Student("745689", "Ana", "Banana"),
                new Student("123659", "Sandra", "Casallas"));

        Mockito.when(repository.findAll()).thenReturn(fluxStudents);

        var response = getAllStudentsUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(3)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

    @Test
    @DisplayName("getAllStudent_Failed")
    void getAllStudents_Failed(){
        Mockito.when(repository.findAll()).thenReturn(Flux.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var response = getAllStudentsUseCase.get();

        StepVerifier.create(response)
                .expectError()
                .verify();

        Mockito.verify(repository).findAll();


    }

}