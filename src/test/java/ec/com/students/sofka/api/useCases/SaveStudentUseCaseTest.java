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
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository iStudentRepository;

    ModelMapper modelMapper;

    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(iStudentRepository, modelMapper);
    }

    @Test
    @DisplayName("saveStudents Success")
    void saveBook() {

        var student = new Student("1069759", "name", "lastName", false);
        var studentDTO = new StudentDTO("1069759", "name", "lastName", false);

        Mockito.when(iStudentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(Mono.just(student));

        var response = saveStudentUseCase.save(studentDTO);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(iStudentRepository).save(ArgumentMatchers.any(Student.class));

    }
}