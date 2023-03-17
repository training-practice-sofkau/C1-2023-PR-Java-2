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
import org.springframework.http.HttpStatus;
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
    void setUp() {
        mapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(repository, mapper);
    }

    @Test
    @DisplayName("getBookById_Success")
    void getBookById(){
        var student = new Student("87483", "Pedro", "Infante");
        student.setId("1");

        Mockito.when(repository.findById(Mockito.any(String.class))).thenReturn(Mono.just(student));

        var result = getStudentByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNext(mapper.map(student, StudentDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repository).findById("1");
    }

    @Test
    @DisplayName("getBookById_Failed")
    void getBookById_Failed(){
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var result = getStudentByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.NOT_FOUND.toString()))
                .verify();

        Mockito.verify(repository).findById("1");
    }
}