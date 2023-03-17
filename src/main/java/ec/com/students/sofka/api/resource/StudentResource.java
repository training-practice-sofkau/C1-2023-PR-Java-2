package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
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

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getStudentById(
            @PathVariable("id") String id
    ) {
        return studentService
                .getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/students")
    private Mono<ResponseEntity<StudentDTO>> saveStudent(
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        return studentService
                .saveStudent(studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/students/update/{id}")
    private Mono<ResponseEntity<StudentDTO>> updateStudent(
            @PathVariable("id") String id,
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        return studentService
                .updateStudent(id, studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(studentDTO, HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>> deleteStudent(
            @PathVariable("id") String id
    ) {
        return studentService
                .deleteStudent(id)
                .map(str -> new ResponseEntity<>("Deleted student with id: " + str, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
