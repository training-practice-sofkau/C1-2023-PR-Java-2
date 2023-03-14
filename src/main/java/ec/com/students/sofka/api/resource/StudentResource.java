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

@RestController
@AllArgsConstructor

public class StudentResource {
    
    @Autowired
    private final StudentServiceImpl studentService;

    @GetMapping("/Students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll() {
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(StudentDTOS -> new ResponseEntity<>(Flux.fromIterable(StudentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

    }

    @GetMapping("/Students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id) {
        return this.studentService.getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(StudentDTO -> new ResponseEntity<>(StudentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }

    @PostMapping("/Students")
    private Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO StudentDTO) {
        return this.studentService
                .saveStudent(StudentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(StudentDTO1 -> new ResponseEntity<>(StudentDTO1, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/Students/{id}")
    private Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @Valid @RequestBody StudentDTO StudentDTO) {
        return this.studentService
                .updateStudent(id, StudentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(StudentDTO1 -> new ResponseEntity<>(StudentDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(StudentDTO, HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/Students/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        return this.studentService
                .deleteStudent(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(s -> new ResponseEntity<>("Deleted " + s, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }


}
