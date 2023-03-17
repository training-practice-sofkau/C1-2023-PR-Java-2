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
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository repository;

    ModelMapper mapper;

    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(repository, mapper);
    }

    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent(){

        var student = new Student("87483", "Pedro", "Infante");
        student.setId("1");

        var monoStudent = Mono.just(student);

        Mockito.when(repository.findById(Mockito.any(String.class))).thenReturn(monoStudent);

        var updatedStudent = new Student("87483", "Sandra", "Casallas");
        updatedStudent.setId("1");

        Mockito.when(repository.save(Mockito.any(Student.class))).thenReturn(Mono.just(updatedStudent));

        var result =
                updateStudentUseCase.update("1", mapper.map(updatedStudent, StudentDTO.class));

        StepVerifier.create(result)
                .expectNext(mapper.map(updatedStudent, StudentDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repository).findById("1");
        Mockito.verify(repository).save(updatedStudent);
    }

    @Test
    @DisplayName("updateStudent_Failed")
    void updateStudent_Failed(){
        var updatedStudent = new Student("87483", "Sandra", "Casallas");
        updatedStudent.setId("1");

        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var result =
                updateStudentUseCase.update("1", mapper.map(updatedStudent, StudentDTO.class));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.NOT_FOUND.toString()))
                .verify();

        Mockito.verify(repository).findById("1");
    }

}