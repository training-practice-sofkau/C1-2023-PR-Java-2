package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    IStudentRepository mockedRepository;
    ModelMapper modelMapper;
    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(mockedRepository, modelMapper);
    }

    @Test
    @DisplayName("getAllStudents_Success")
    void getAllStudents() {

        var student1 = new Student("StudentId",
                "123456",
                "John",
                "Lincoln",
                true,
                List.of("Atomic Habits")
        );

        var student2 = new Student("StudentId",
                "155948",
                "James",
                "Stewart",
                false,
                List.of("Thrives")
        );

        var fluxStudents = Flux.just(student1, student2);

        Mockito.when(mockedRepository.findAll()).thenReturn(fluxStudents);

        var response = getAllStudentsUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(mockedRepository).findAll();
    }
}