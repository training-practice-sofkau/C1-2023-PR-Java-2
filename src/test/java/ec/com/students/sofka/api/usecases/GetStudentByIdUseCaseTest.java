package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;
    @InjectMocks
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getStudentById_successfully")
    void getStudentId() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        var monoStudent = Mono.just(newStudent);
        StudentDTO newStudentDTO = new StudentDTO("1", "name1", "lastname1");
        newStudentDTO.setId(id);
        newStudentDTO.setBlocked(false);


        when(repoMock.findById(id)).thenReturn(monoStudent);


        Mono<StudentDTO> response = getStudentByIdUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext(newStudentDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());

    }

    @Test
    @DisplayName("testStudentNotFound")
    public void testStudentNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<StudentDTO> response = getStudentByIdUseCase.apply(id);

        StepVerifier.create(response)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
    }

}