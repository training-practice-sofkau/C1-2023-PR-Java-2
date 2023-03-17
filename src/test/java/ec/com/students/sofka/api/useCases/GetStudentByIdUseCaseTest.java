package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
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
class GetStudentByIdUseCaseTest {

    @Mock
    IStudentRepository iStudentRepository;
    ModelMapper modelMapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(iStudentRepository, modelMapper);
    }

    @Test
    @DisplayName("getByIdStudents Success")
    void getBookById(){

        var student = Mono.just(new Student("1069", "name1", "lastName1", false));

        Mockito.when(iStudentRepository.findById(ArgumentMatchers.anyString())).thenReturn(student);

        var response = getStudentByIdUseCase.apply("studentId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(iStudentRepository).findById("studentId");
    }

}