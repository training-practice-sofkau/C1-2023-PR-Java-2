package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class StudentResource {

    private StudentServiceImpl studentService;

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAllStudents() {
        return studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }
}
