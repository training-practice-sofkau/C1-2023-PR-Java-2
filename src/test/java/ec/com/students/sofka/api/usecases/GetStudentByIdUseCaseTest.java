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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository studentRepository;

    ModelMapper modelMapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(studentRepository, modelMapper);
    }

    @Test
    @DisplayName("Get student by Id successfully")
    void successfullyScenario() {

        Student s1 = new Student("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook"));
        var monoStudent = Mono.just(s1);

        Mockito.when(studentRepository.findById("testId")).thenReturn(monoStudent);

        var response = getStudentByIdUseCase.getStudentById("testId");

        StepVerifier.create(response)
                .expectNext(new StudentDTO("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook")))
                .verifyComplete();

        Mockito.verify(studentRepository).findById("testId");
    }

    @Test
    @DisplayName("Get student by Id fails")
    void failedScenario() {

        Mockito.when(studentRepository.findById("testId")).thenReturn(Mono.empty());

        var response = getStudentByIdUseCase.getStudentById("testId");

        StepVerifier.create(response)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(studentRepository).findById("testId");
    }
}