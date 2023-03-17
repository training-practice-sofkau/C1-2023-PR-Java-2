package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init() {
        mapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(repository,mapper);
    }

    @Test
    void updateStudent(){

        var student = new Student(
                "123456",
                "Peter",
                "Parker"
        );

        var updatedStudent = new Student(
                "123456",
                "Miles",
                "Morales"
        );


        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(student));

        Mockito.when(repository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(updatedStudent));

        var response = updateStudentUseCase.update("",mapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectNext(mapper.map(updatedStudent, StudentDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
        Mockito.verify(repository).save(ArgumentMatchers.any(Student.class));

    }

    @Test
    @DisplayName("updateStudentInvalidId_Fail")
    void updateStudentInvalidId_Fail() {

        var student = new Student(
                "123456",
                "Peter",
                "Parker"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = updateStudentUseCase.update("",mapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());

    }

}