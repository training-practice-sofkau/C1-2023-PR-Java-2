package ec.com.students.sofka.api.router;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.usecases.*;
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
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUsecase getAllStudentsUsecase) {
        return route(GET("/student"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllStudentsUsecase.get(), StudentDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getStudentById(GetStudentByIdUsecase getStudentByIdUsecase) {
        return route(GET("/student/{id}"),
                request -> getStudentByIdUsecase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveStudent(SaveStudentUsecase saveStudentUsecase) {
        return route(POST("/student").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> saveStudentUsecase.save(studentDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }

    @Bean
    public RouterFunction<ServerResponse> patchStudent(UpdateStudentUsecase updateStudentUsecase) {
        return route(PUT("/student/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> updateStudentUsecase.update(request.pathVariable("id"), studentDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteStudent(DeleteUsecase deleteUsecase) {
        return route(DELETE("/student/{id}"),
                request -> deleteUsecase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Deleted Successfully"))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }
}
