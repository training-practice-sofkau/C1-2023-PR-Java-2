package ec.com.students.sofka.api.usecases;


import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SaveStudentUseCaseTest {

    @Mock
    IStudentRepository studentRepository;

    ModelMapper modelMapper;
    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(studentRepository, modelMapper);
    }

    @Test
    @DisplayName("Save student successfully")
    void successfullyScenario() {

        Student s1 = new Student("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook"));
        var monoStudent = Mono.just(s1);

        Mockito.when(studentRepository.save(new Student("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook"))))
                .thenReturn(monoStudent);

        var response = saveStudentUseCase.save(modelMapper.map(s1, StudentDTO.class));

        StepVerifier.create(response)
                .expectNext(new StudentDTO("testId", "idNum", "testStudent", "testStudent", false, List.of("testBook")))
                .verifyComplete();
    }

}