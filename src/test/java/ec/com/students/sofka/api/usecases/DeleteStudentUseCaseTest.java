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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseTest {

    @Mock
    IStudentRepository studentRepository;

    ModelMapper modelMapper;
    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        deleteStudentUseCase = new DeleteStudentUseCase(studentRepository, modelMapper);
    }

    @Test
    @DisplayName("Delete student successfully")
    void successfullyScenario() {
        Student s1 = new Student("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook"));
        Mockito.when(studentRepository.findById("testId")).thenReturn(Mono.just(s1));
        Mockito.when(studentRepository.deleteById("testId")).thenReturn(Mono.empty());

        var response = deleteStudentUseCase.delete("testId");

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Delete student fails")
    void failedScenario() {

        Mockito.when(studentRepository.findById("testId")).thenReturn(Mono.empty());

        var result = deleteStudentUseCase.delete("testId");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Student not found"))
                .verify();
    }
}