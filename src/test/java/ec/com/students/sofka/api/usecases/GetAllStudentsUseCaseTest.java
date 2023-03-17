package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    IStudentRepository studentRepository;

    ModelMapper modelMapper;
    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(studentRepository, modelMapper);
    }

    @Test
    @DisplayName("Get all students successfully")
    void successfullyScenario() {

        Student s1 = new Student("testId1", "idNum1", "testStudent1", "testStudent1", false, List.of("testBook1"));
        Student s2 = new Student("testId2", "idNum2", "testStudent2", "testStudent2", false, List.of("testBook2"));
        var fluxStudents = Flux.just(s1, s2);

        Mockito.when(studentRepository.findAll()).thenReturn(fluxStudents);

        var response = getAllStudentsUseCase.getAll();

        StepVerifier.create(response)
                .expectNext(new StudentDTO("testId1", "idNum1", "testStudent1", "testStudent1", false, List.of("testBook1")))
                .expectNext(new StudentDTO("testId2", "idNum2", "testStudent2", "testStudent2", false, List.of("testBook2")))
                .verifyComplete();

        Mockito.verify(studentRepository).findAll();
    }
}