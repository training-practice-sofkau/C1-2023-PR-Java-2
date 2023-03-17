package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;
    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getAllStudents_Success")
    void getAllStudents() {
        //Build the scenario you need
        var fluxStudents = Flux.just(new Student("1", "title1", "lastname1"), new Student("2", "title2", "lastname2"));

        Mockito.when(repoMock.findAll()).thenReturn(fluxStudents);

        var response = getAllStudentsUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();
    }

}