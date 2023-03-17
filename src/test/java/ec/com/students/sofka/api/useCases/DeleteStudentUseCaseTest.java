package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
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
class DeleteStudentUseCaseTest {

    @Mock
    IStudentRepository iStudentRepository;

    ModelMapper modelMapper;

    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        deleteStudentUseCase = new DeleteStudentUseCase(iStudentRepository);
    }

    @Test
    void deleteBook(){

        var student1 =  new Student("1069", "name1", "lastName1", false);
        var student = Mono.just(student1);

        Mockito.when(iStudentRepository.findById(ArgumentMatchers.anyString())).thenReturn(student);
        Mockito.when(iStudentRepository.delete(ArgumentMatchers.any(Student.class))).thenReturn(Mono.empty());

        var response = deleteStudentUseCase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(iStudentRepository).findById(ArgumentMatchers.anyString());
        Mockito.verify(iStudentRepository).delete(ArgumentMatchers.any(Student.class));
    }
}