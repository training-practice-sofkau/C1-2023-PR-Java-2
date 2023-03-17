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
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    ModelMapper modelMapper;
    @InjectMocks
    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("saveBook_successfully")
    void saveBook() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        StudentDTO newStudentDTO = new StudentDTO("1", "name1", "lastname1");
        newStudentDTO.setId(id);
        newStudentDTO.setBlocked(false);


        when(repoMock.save(newStudent)).thenReturn(Mono.just(newStudent));


        Mono<StudentDTO> response = saveStudentUseCase.save(newStudentDTO);

        StepVerifier.create(response)
                .expectNext(newStudentDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("testSaveBookError")
    public void testSaveBookError() {
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        StudentDTO newStudentDTO = new StudentDTO("1", "name1", "lastname1");
        newStudentDTO.setId(id);
        newStudentDTO.setBlocked(false);

        when(repoMock.save(newStudent)).thenReturn(Mono.empty());

        Mono<StudentDTO> result = saveStudentUseCase.save(newStudentDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Student.class));
    }

}