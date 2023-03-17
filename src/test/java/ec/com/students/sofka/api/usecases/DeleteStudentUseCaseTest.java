package ec.com.students.sofka.api.usecases;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.repository.IStudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class DeleteStudentUseCaseTest {

    @Mock
    IStudentRepository repoMock;

    @InjectMocks
    DeleteStudentUseCase deleteStudentUseCase;

    @Test
    @DisplayName("deleteStudentTest_Success")
    void deleteStudent() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Student newStudent = new Student("1", "name1", "lastname1");
        newStudent.setId(id);
        var monoStudent = Mono.just(newStudent);

        when(repoMock.findById(id)).thenReturn(monoStudent);
        when(repoMock.delete(newStudent)).thenReturn(Mono.empty());


        Mono<String> response = deleteStudentUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext("6413683efa74e77204d881f0")
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, times(1)).delete(ArgumentMatchers.any(Student.class));

    }

    @Test
    @DisplayName("testDeleteBookNotFound")
    public void testDeleteBookNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<String> result = deleteStudentUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, never()).delete(ArgumentMatchers.any(Student.class));
    }

}