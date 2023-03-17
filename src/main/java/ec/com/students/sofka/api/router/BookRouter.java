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
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {
    @Bean
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUseCase getAllStudentsUseCase){
        return route(GET("/students"),
                request -> getAllStudentsUseCase.get()
                .collectList()
                .flatMap(StudentDTOS -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllStudentsUseCase.get(), StudentDTO.class)))
                .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }

    @Bean
    public RouterFunction<ServerResponse> getStudentsById(GetStudentByIdUseCase getStudentByIdUseCase){
        return route(GET("/students/{id}"),
                request -> getStudentByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(studentDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(studentDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveBook(SaveStudentUseCase saveStudentUseCase){
        return route(POST("/students").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(studentDTO -> saveStudentUseCase.save(studentDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }

    @Bean
    public RouterFunction<ServerResponse> updateBook(UpdateStudentUseCase updateStudentUseCase){
        return route(PUT("/books/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .switchIfEmpty(Mono.error(new Throwable()))
                        .flatMap(studentDTO -> updateStudentUseCase.update(request.pathVariable("id"), studentDTO))
                        .flatMap(result -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteBook(DeleteStudentUseCase deleteStudentUseCase){
        return route(DELETE("/books/{id}"),
                request -> deleteStudentUseCase.apply(request.pathVariable("id"))
                        .flatMap(string -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("The book with the fabulous id: " + string + " was deleted"))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }


}
