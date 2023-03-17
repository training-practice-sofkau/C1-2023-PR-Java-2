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
import org.springframework.http.HttpStatus;
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
    void setUp() {
        mapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(repository, mapper);
    }

    @Test
    @DisplayName("saveStudent_Success")
    void saveStudent(){
        var student = new Student("87483", "Pedro", "Infante");

        var monoStudent = Mono.just(student);

        Mockito.when(repository.save(Mockito.any(Student.class))).thenReturn(monoStudent);

        var result = saveStudentUseCase.save(mapper.map(student, StudentDTO.class));

        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();

        Mockito.verify(repository).save(student);
    }

    @Test
    @DisplayName("saveStudent_Failed")
    void saveStudent_Failed(){
        var student = new Student("87483", "Pedro", "Infante");

        Mockito.when(repository.save(Mockito.any(Student.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.BAD_REQUEST.toString())));

        var result = saveStudentUseCase.save(mapper.map(student, StudentDTO.class));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.BAD_REQUEST.toString()))
                .verify();

        Mockito.verify(repository).save(student);
    }
}