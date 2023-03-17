package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.repository.StudentRepository;
import ec.com.students.sofka.api.util.InstanceProvider;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        deleteStudentUseCase = new DeleteStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getStudentById_Success")
    void deleteStudent(){
        var studentID = "ID1";
        var student = Mono.just(InstanceProvider.getStudent());

        Mockito.when(repoMock.findById(studentID)).thenReturn(student);
        Mockito.when(repoMock.deleteById(studentID)).thenReturn(Mono.empty());

        var service = deleteStudentUseCase.apply(studentID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).deleteById(studentID);
    }

}