package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
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
    void getAllBooks(){
        var studentDTO =mapper.map(InstanceProvider.getStudent(), StudentDTO.class);

        Mockito.when(repoMock.save(mapper.map(studentDTO, Student.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        var service = saveStudentUseCase.apply(studentDTO);

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).save(mapper.map(studentDTO, Student.class));
    }

}