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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetAllStudentsUsecaseTest {
    @Mock
    IStudentRepository repository;
    ModelMapper modelMapper;
    GetAllStudentsUsecase getAllStudentsUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        getAllStudentsUsecase = new GetAllStudentsUsecase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAllstudents_Success")
    void getAllstudents() {

        Student student = new Student();
        student.setIdNumber("Test id");
        student.setName("Test name");
        student.setLastName("Test last name");

        Student student2 = new Student();
        student.setIdNumber("Test id2");
        student.setName("Test name2");
        student.setLastName("Test last name2");

        Mockito.when(repository.findAll()).
                thenAnswer(InvocationOnMock -> {
                    return Flux.just(student, student2);
                });

        Flux<StudentDTO> response = getAllStudentsUsecase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}