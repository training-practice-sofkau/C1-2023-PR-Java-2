package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
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

@ExtendWith(MockitoExtension.class)
class UpdateStudentUseCaseTest {

    @Mock
    IStudentRepository iStudentRepository;
    ModelMapper modelMapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(iStudentRepository, modelMapper);
    }


    @Test
    @DisplayName("Update book successfully")
    void successfullyScenario() {
        var student =  new Student("1069", "name1", "lastName1", false);
        var studentUpdated = new Student("1069", "name1 updated", "lastName1 updated", false);
        var studentDTO = new StudentDTO("1069", "name1 updated", "lastName1 updated", false);

        Mockito.when(iStudentRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(student));
        Mockito.when(iStudentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(studentUpdated));


        var response = updateStudentUseCase.update("testBook", studentDTO);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(iStudentRepository).findById(ArgumentMatchers.any(String.class));
        Mockito.verify(iStudentRepository).save(ArgumentMatchers.any(Student.class));


    }


}