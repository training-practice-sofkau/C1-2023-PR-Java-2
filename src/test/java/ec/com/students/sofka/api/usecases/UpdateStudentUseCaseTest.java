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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository studentRepository;

    ModelMapper modelMapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(studentRepository, modelMapper);
    }

    @Test
    @DisplayName("Update student successfully")
    void successfullyScenario() {

        Student s1 = new Student("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook"));
        var monoStudent = Mono.just(s1);

        Mockito.when(studentRepository.findById("testId")).thenReturn(monoStudent);

        Student s2 = new Student("testId", "idNum2", "testStudent2", "testStudent2", true, List.of("testBook2"));
        var monoStudent2 = Mono.just(s2);
        Mockito.when(studentRepository.save(s2)).thenReturn(monoStudent2);

        var response = updateStudentUseCase.update(
                "testId",
                new StudentDTO("testId", "idNum2", "testStudent2", "testStudent2", true, List.of("testBook2"))
        );


        StepVerifier.create(response)
                .expectNext(new StudentDTO("testId", "idNum2", "testStudent2", "testStudent2", true, List.of("testBook2")))
                .verifyComplete();
    }

    @Test
    @DisplayName("Update student - student not found")
    void failedScenario() {

        Mockito.when(studentRepository.findById("testId")).thenReturn(Mono.empty());

        var response = updateStudentUseCase.update("testId", new StudentDTO("testId", "idNum2", "testStudent2", "testStudent2", true, List.of("testBook2")));

        StepVerifier.create(response)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Student not found"))
                .verify();
    }
}