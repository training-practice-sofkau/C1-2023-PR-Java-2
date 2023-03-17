package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

//@RestController
@AllArgsConstructor

public class StudentResource {
    
    @Autowired
    private final StudentServiceImpl studentService;

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll() {
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(StudentDTOS -> new ResponseEntity<>(Flux.fromIterable(StudentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

    }

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id) {
        return this.studentService.getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(StudentDTO -> new ResponseEntity<>(StudentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/students")
    private Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO studentDTO) {
        return this.studentService
                .saveStudent(studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(StudentDTO1 -> new ResponseEntity<>(StudentDTO1, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @Valid @RequestBody StudentDTO studentDTO) {
        return this.studentService
                .updateStudent(id, studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(StudentDTO1 -> new ResponseEntity<>(StudentDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(studentDTO, HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        return this.studentService
                .deleteStudent(id)
                //.switchIfEmpty(Mono.error(new Throwable("Empty response from service")))
                .map(s -> new ResponseEntity<>("Student deleted "+s, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

}
