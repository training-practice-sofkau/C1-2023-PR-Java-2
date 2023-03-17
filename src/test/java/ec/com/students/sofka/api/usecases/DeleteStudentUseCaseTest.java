package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
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
    StudentRepository studentRepository;
    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void setUp() {
        deleteStudentUseCase = new DeleteStudentUseCase(studentRepository);
    }

    @Test
    void testDeleteStudent() {
        // create a test student
        Student student = new Student(
                "443322ID",
                "123456789",
                "John",
                "Doe",
                true,
                new ArrayList<>()
        );

        // save the student to the database
        Mockito.when(studentRepository.findById("443322ID")).thenReturn(Mono.just(student));
        Mockito.when(studentRepository.delete(student)).thenReturn(Mono.empty());

        // delete the student using the use case
        Mono<Void> result = deleteStudentUseCase.apply("443322ID");

        // verify that the student was deleted
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testDeleteNonExistentStudent() {
        Mockito.when(studentRepository.findById("443322ID")).thenReturn(Mono.empty());

        // try to delete a student that doesn't exist using the use case
        Mono<Void> result = deleteStudentUseCase.apply("443322ID");

        // verify that an exception was thrown with the expected message
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Error deleting student, not found"))
                .verify();
    }


}