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

@ExtendWith(MockitoExtension.class)
class DeleteStudentByIdUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    DeleteStudentByIdUseCase deleteStudentByIdUseCase;

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        deleteStudentByIdUseCase = new DeleteStudentByIdUseCase(repository, mapper);
    }

    @Test
    @DisplayName("deleteBookById_Success")
    void deleteBookById(){

        var student = new Student("87483", "Pedro", "Infante");
        student.setId("1");

        Mockito.when(repository.findById(Mockito.any(String.class))).thenReturn(Mono.just(student));

        Mockito.when(repository.deleteById(Mockito.any(String.class))).thenReturn(Mono.empty());

        var result = deleteStudentByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        Mockito.verify(repository).findById("1");
        Mockito.verify(repository).deleteById("1");
    }

    @Test
    @DisplayName("deleteBookById_Failed")
    void deleteBookById_Failed(){
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var result = deleteStudentByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.NOT_FOUND.toString()))
                .verify();

        Mockito.verify(repository).findById("1");
    }
}