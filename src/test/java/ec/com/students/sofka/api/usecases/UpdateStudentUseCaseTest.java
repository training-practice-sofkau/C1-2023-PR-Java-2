package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
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
class UpdateStudentUseCaseTest {


    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getAllStudents_Success")
    void updateStudent(){
        var studentID = "ID1";
        var oldStudent = Mono.just(InstanceProvider.getStudent());
        var newStudentStudent = InstanceProvider.getStudentToUpdate();
        var newStudent = Mono.just(InstanceProvider.getStudentToUpdate());

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(oldStudent);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Student.class))).thenReturn(newStudent);

        var service = updateStudentUseCase.apply(studentID,
                mapper.map(newStudentStudent, StudentDTO.class));

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("updateStudentById_NonSuccess")
    void updateStudentByNonExistingID(){
        var studentID = "ID1";
        var newStudentStudent = InstanceProvider.getStudentToUpdate();
        var newStudent = Mono.just(InstanceProvider.getStudentToUpdate());

        Mockito.when(repoMock.findById(studentID)).thenReturn(Mono.empty());

        var service = updateStudentUseCase.apply(studentID,
                mapper.map(newStudentStudent, StudentDTO.class));

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);

        //Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }

}