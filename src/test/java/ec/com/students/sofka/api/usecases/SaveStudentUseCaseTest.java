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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository mockedRepository;
    ModelMapper modelMapper;
    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("saveStudent_Success")
    void saveStudent(){

        var student = new Student("StudentId",
                "123456",
                "John",
                "Lincoln",
                true,
                List.of("Atomic Habits")
        );

        var studentDTO = new StudentDTO("StudentId",
                "123456",
                "John",
                "Lincoln",
                true,
                List.of("Atomic Habits")
        );

        Mockito.when(mockedRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(student));

        var response = saveStudentUseCase.apply(studentDTO);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(mockedRepository).save(ArgumentMatchers.any(Student.class));
    }
}