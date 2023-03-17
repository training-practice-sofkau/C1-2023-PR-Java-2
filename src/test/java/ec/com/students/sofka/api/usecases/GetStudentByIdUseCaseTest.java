package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
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
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository mockedRepository;
    ModelMapper modelMapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("getStudentById_Success")
    void getStudentById() {

        var student = Mono.just(new Student("studentId",
                "123456",
                "John",
                "Lincoln",
                true,
                List.of("Atomic Habits")
        ));

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(student);

        var response = getStudentByIdUseCase.apply("studentId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(mockedRepository).findById("studentId");
    }

    @Test
    @DisplayName("getStudentByWrongId_NonSuccess")
    void getStudentByWrongId() {

        Mockito.when(mockedRepository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = getStudentByIdUseCase.apply("studentId");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(mockedRepository).findById("studentId");
    }
}