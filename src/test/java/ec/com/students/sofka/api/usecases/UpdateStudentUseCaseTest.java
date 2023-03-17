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
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;
    @InjectMocks
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("updateStudent_successfully")
    void updateStudent() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        Student studentUpdated = new Student("2", "name2", "lastname2");
        studentUpdated.setId(id);
        StudentDTO studentUpdatedDTO = new StudentDTO(studentUpdated.getIdNum(), studentUpdated.getName(), studentUpdated.getLastName());
        studentUpdatedDTO.setBlocked(false);


        when(repoMock.findById(id)).thenReturn(Mono.just(newStudent));
        when(repoMock.save(studentUpdated)).thenReturn(Mono.just(studentUpdated));


        Mono<StudentDTO> response = updateStudentUseCase.update(id, studentUpdatedDTO);

        StepVerifier.create(response)
                .expectNext(studentUpdatedDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("testUpdateStudentError")
    public void testUpdateStudentError() {
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        Student studentUpdated = new Student("2", "name2", "lastname2");
        newStudent.setId(id);
        StudentDTO studentUpdatedDTO = new StudentDTO(studentUpdated.getIdNum(), studentUpdated.getName(), studentUpdated.getLastName());
        studentUpdatedDTO.setId(id);
        studentUpdatedDTO.setBlocked(false);

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<StudentDTO> result = updateStudentUseCase.update(id, studentUpdatedDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
    }

}