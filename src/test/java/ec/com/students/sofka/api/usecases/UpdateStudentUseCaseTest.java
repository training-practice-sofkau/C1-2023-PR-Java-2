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
    void updateStudent() {

        var student = new Student("StudentId",
                        "123456",
                        "John",
                        "Lincoln",
                        true,
                        List.of("Atomic Habits")
        );

        var updatedStudent = new Student("StudentId",
                "326859",
                "Thomas",
                "Lincoln",
                false,
                List.of("Arabian Nights")
        );

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(student));

        Mockito.when(mockedRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(updatedStudent));

        var response = updateStudentUseCase.apply("studentId",
                modelMapper.map(updatedStudent, StudentDTO.class)
        );

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(mockedRepository).findById(ArgumentMatchers.anyString());
        Mockito.verify(mockedRepository).save(ArgumentMatchers.any(Student.class));
    }
}