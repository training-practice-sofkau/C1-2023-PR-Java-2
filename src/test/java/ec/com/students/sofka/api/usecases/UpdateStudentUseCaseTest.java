package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
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
class UpdateStudentUseCaseTest {

    @Mock
    StudentRepository studentRepository;
    ModelMapper modelMapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(studentRepository, modelMapper);
    }

    @Test
    void testUpdateStudent() {

        Student student = new Student("443322ID", "123456789", "John", "Doe", true, new ArrayList<>());

        Mockito.when(studentRepository.findById("443322ID")).thenReturn(Mono.just(student));

        StudentDTO studentDTO = new StudentDTO("443322ID", "123456789", "John", "Doe", false, new ArrayList<>());
        Student studentF = new Student("443322ID", "123456789", "John", "Doe", false, new ArrayList<>());

        Mockito.when(studentRepository.save(Mockito.any())).thenReturn(Mono.just(studentF));

        var result = updateStudentUseCase.apply("443322ID", studentDTO);

        StepVerifier.create(result)
                .expectNextMatches(studentDTO1 -> {
                    assertEquals("443322ID", studentDTO1.getId());
                    assertEquals("123456789", studentDTO1.getIdNumber());
                    assertEquals("John", studentDTO1.getName());
                    assertEquals("Doe", studentDTO1.getLastName());
                    assertFalse(studentDTO1.getActive());
                    return true;
                })
                .expectComplete()
                .verify();

    }

}