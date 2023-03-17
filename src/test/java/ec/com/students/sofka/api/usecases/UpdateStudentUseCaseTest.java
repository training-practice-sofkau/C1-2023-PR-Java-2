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
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository mockedRepository;
    ModelMapper modelMapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent() {

        var student = new Student("studentId",
                        "123456",
                        "John",
                        "Lincoln",
                        true,
                        List.of("Atomic Habits")
        );

        Mockito.when(mockedRepository.findById("studentId")).thenReturn(Mono.just(student));

        Mockito.when(mockedRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(student));

        var response = updateStudentUseCase.apply("studentId",
                modelMapper.map(student, StudentDTO.class)
        );

        StepVerifier.create(response)
                .expectNext(modelMapper.map(student, StudentDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
        Mockito.verify(mockedRepository).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("updateStudent_NonSuccess")
    void updateInvalidStudent() {

        var student = new Student("StudentId",
                "123456",
                "John",
                "Lincoln",
                true,
                List.of("Atomic Habits")
        );

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = updateStudentUseCase.apply("",modelMapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
    }
}