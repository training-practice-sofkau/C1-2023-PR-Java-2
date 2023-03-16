package ec.com.students.sofka.api.router;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUseCase studentsUseCase) {
        return route(GET("/students/"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentsUseCase.get(), Student.class)
                        .onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getStudentById(GetStudentByIdUseCase studentByIdUseCase) {
        return route(GET("/students/{id}"),
                request -> studentByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO)
                        )
                        .onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> saveStudent(SaveNewStudentUseCase saveNewStudentUseCase) {
        return route(POST("/students/").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> saveNewStudentUseCase.apply(studentDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)
                                )
                        )
                        .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST).build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateStudent(UpdateStudentUseCase updateStudentUseCase) {
        return route(PUT("/students/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> updateStudentUseCase.apply(request.pathVariable("id"), studentDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)
                                )
                        )
                        .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST).build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteStudent(DeleteStudentUseCase deleteStudentUseCase) {
        return route(DELETE("/students/{id}"),
                request -> deleteStudentUseCase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO)
                        )
                        .onErrorResume(error -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("error", error.getMessage()))
                        )
        );
    }
}
