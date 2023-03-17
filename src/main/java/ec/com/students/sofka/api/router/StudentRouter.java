package ec.com.students.sofka.api.router;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.useCases.*;
import ec.com.students.sofka.api.useCases.interfaces.UpdateStudent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> saveStudents(SaveStudentUseCase saveStudentUseCase) {
        return route(POST("/students").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> saveStudentUseCase.save(studentDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }


    @Bean
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUseCase getAllStudentsUseCase) {
        return route(GET("/students"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllStudentsUseCase.get(), StudentDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getStudentById(GetStudentByIdUseCase getStudentByIdUseCase) {
        return route(GET("/students/{id}"),
                request -> getStudentByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> updateStudent(UpdateStudentUseCase updateStudentUseCase){
        return route(PUT("/students/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> updateStudentUseCase.update(request.pathVariable("id"), studentDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteStudents(DeleteStudentUseCase deleteStudentUseCase){
        return  route(DELETE("/students/{id}"),
                request -> deleteStudentUseCase.delete(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Student Deleted"))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
                );
    }
}
