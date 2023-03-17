package ec.com.students.sofka.api.useCases;

import ec.com.students.sofka.api.domain.collection.Student;
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
class GetAllStudentsUseCaseTest {
    @Mock
    IStudentRepository iStudentRepository;

    ModelMapper modelMapper;

    GetAllStudentsUseCase service;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        service = new GetAllStudentsUseCase(iStudentRepository, modelMapper);
    }

    @Test
    @DisplayName("getAllStudents Success")
    void getAllBooks() {
        //Build the scenario you need
        var fluxStudents = Flux.just(
                new Student("1069", "name1", "lastName1", false),
                new Student("1050", "name2", "lastName2", true),
                new Student("1010", "name3", "lastName3", true),
                new Student("1040", "name4", "lastName4", false)
        );

        Mockito.when(iStudentRepository.findAll()).thenReturn(fluxStudents);

        var response = service.get();

        StepVerifier.create(response)
                .expectNextCount(4)
                .verifyComplete();

        Mockito.verify(iStudentRepository).findAll();

    }

}

