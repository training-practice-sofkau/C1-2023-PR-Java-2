package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.util.InstanceProvider;
import org.assertj.core.error.ShouldNotMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveStudentUseCaseTest {
    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveStudent_Success")
    void SaveStudent(){
        var studentDTO =mapper.map(InstanceProvider.getStudent(), StudentDTO.class);

        Mockito.when(repoMock.save(mapper.map(studentDTO, Student.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        var service = saveStudentUseCase.apply(studentDTO);

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).save(mapper.map(studentDTO, Student.class));
    }

   @Test
    @DisplayName("saveInvalidStudent_NonSuccess")
    void SaveInvalidStudent(){

        Student student = new Student("Id", null, "name", "lastname", false, new ArrayList<>());
        //Mockito.when(repoMock.save(student))
             //   .thenAnswer(Mono.error(new Throwable("ss")));
      /*  Mockito.when(repoMock.save(Objects.requireNonNull(AdditionalMatchers.not(ArgumentMatchers.any(Student.class)))))
                .thenThrow(new Throwable("Invalid Argument"));*/

        var service = saveStudentUseCase.apply(mapper.map(student, StudentDTO.class));

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        //Mockito.verify(repoMock).save(mapper.map(studentDTO, Student.class));
    }

}