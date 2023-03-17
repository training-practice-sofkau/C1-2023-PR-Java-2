package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    StudentRepository studentRepository;
    ModelMapper modelMapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(studentRepository, modelMapper);
    }

    @Test
    void testGetStudentById() {

        var student = new Student("443322ID", "123456789", "John", "Doe", true, new ArrayList<>());

        Mockito.when(studentRepository.findById("443322ID")).thenReturn(Mono.just(student));

        var result = getStudentByIdUseCase.apply("443322ID");

        StepVerifier.create(result)
                .expectNextMatches(studentDTO -> {
                    assertEquals("443322ID", studentDTO.getId());
                    assertEquals("123456789", studentDTO.getIdNumber());
                    assertEquals("John", studentDTO.getName());
                    assertEquals("Doe", studentDTO.getLastName());
                    assertTrue(studentDTO.getActive());
                    return true;
                })
                .expectComplete()
                .verify();

    }

    @Test
    void testGetNonExistentStudentById() {

        Mockito.when(studentRepository.findById("443322ID")).thenReturn(Mono.empty());

        var result = getStudentByIdUseCase.apply("443322ID");

        StepVerifier.create(result)
                .expectComplete()
                .verify();

    }

}