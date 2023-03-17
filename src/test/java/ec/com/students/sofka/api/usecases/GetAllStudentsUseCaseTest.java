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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    StudentRepository studentRepository;
    ModelMapper modelMapper;

    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(studentRepository, modelMapper);
    }

    @Test
    void testGetAllStudents() {
        // create some test students
        List<Student> students = List.of(
                new Student("443322ID", "123456789", "John", "Doe", true, new ArrayList<>()),
                new Student("554433ID", "987654321", "Jane", "Doe", false, new ArrayList<>())
        );

        // mock the repository to return the test students
        Mockito.when(studentRepository.findAll()).thenReturn(Flux.fromIterable(students));

        // call the use case to get all students
        Flux<StudentDTO> result = getAllStudentsUseCase.get();

        // verify that the DTOs are the expected ones
        StepVerifier.create(result)
                .expectNext(new StudentDTO("443322ID", "123456789", "John", "Doe", true, new ArrayList<>()))
                .expectNext(new StudentDTO("554433ID", "987654321", "Jane", "Doe", false, new ArrayList<>()))
                .expectComplete()
                .verify();
    }

    @Test
    void testGetNoStudents() {
        // mock the repository to return no students
        Mockito.when(studentRepository.findAll()).thenReturn(Flux.empty());

        // call the use case to get all students
        Flux<StudentDTO> result = getAllStudentsUseCase.get();

        // verify that no DTOs are emitted
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

}