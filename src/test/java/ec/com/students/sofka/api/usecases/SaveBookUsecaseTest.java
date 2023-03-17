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

@ExtendWith(MockitoExtension.class)
class SaveBookUsecaseTest {
    @Mock
    IStudentRepository repository;
    ModelMapper modelMapper;
    SaveStudentUsecase saveBookUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveBookUsecase = new SaveStudentUsecase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {

        Student student = new Student();
        student.setIdNumber("Test id");
        student.setName("Test name");
        student.setLastName("Test last name");

        Mockito.when(repository.save(student)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(student);
                });

        Mono<StudentDTO> response = saveBookUsecase.save(modelMapper.map(student, StudentDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(student);
    }


}