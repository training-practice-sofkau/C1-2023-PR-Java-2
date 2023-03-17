package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
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
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(repository, mapper);
    }

    @Test
    void saveStudent(){

        var student = new Student(
                "123456",
                "Peter",
                "Parker"
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(student));

        var response = saveStudentUseCase.save(mapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(ArgumentMatchers.any(Student.class));

    }

    @Test
    void saveStudent_Fail(){

        var student = new Student(
                "123456",
                null,
                "Parker"
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.empty());

        var response = saveStudentUseCase.save(mapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).save(ArgumentMatchers.any(Student.class));

    }

}