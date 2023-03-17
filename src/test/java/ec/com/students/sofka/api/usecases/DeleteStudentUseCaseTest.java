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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseTest {

    @Mock
    IStudentRepository repository;

    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void init(){
        deleteStudentUseCase = new DeleteStudentUseCase(repository);
    }

    @Test
    void deleteStudent(){

        var student = Mono.just(new Student(
                        "336598",
                        "Peter",
                        "Parker"
                )
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(student);

        Mockito.when(repository.delete(ArgumentMatchers.any(Student.class))).thenReturn(Mono.empty());

        var response = deleteStudentUseCase.delete("");

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).delete(ArgumentMatchers.any(Student.class));
    }

}