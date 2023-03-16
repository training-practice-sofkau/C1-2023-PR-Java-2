package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.util.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getStudentById_Success")
    void getStudentById(){
        var studentID = "ID1";
        var student = Mono.just(InstanceProvider.getStudent());

        Mockito.when(repoMock.findById(studentID)).thenReturn(student);

        var service = getStudentByIdUseCase.apply(studentID);

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);
    }
}