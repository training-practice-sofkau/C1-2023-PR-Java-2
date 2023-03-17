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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    DeleteStudentUseCase service;

    @BeforeEach
    void init(){

        service = new DeleteStudentUseCase(repoMock);
    }

    @Test
    @DisplayName("deleteStudent_Success")
    void deleteStudent(){

        Student student = new Student("testId", "56892345", "Pepito", "Perez", true, new ArrayList<>());


        Mockito.when(repoMock.findById("testId")).thenReturn(Mono.just(student));
        Mockito.when(repoMock.delete(student)).thenReturn(Mono.empty());

        var response = service.apply("testId");

        StepVerifier.create(response)
                .expectNext("testId")
                .expectComplete()
                .verify();

    }

}