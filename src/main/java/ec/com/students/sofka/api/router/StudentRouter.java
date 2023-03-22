package ec.com.students.sofka.api.router;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.usecases.CreateStudentUsecase;
import ec.com.students.sofka.api.usecases.GetAllStudentsUsecase;
import ec.com.students.sofka.api.usecases.GetStudentByIdUsecase;
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
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUsecase getAllStudentsUsecase){
        return route(GET("/students"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllStudentsUsecase.get(), StudentDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveStudent(CreateStudentUsecase createStudentUsecase){
        return route(POST("/students").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(bookDTO -> createStudentUsecase.create(bookDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }

    @Bean
    public RouterFunction<ServerResponse> getStudentById(GetStudentByIdUsecase getStudentByIdUsecase){
        return route(GET("/students/{id}"),
                request -> getStudentByIdUsecase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }
}
