package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class StudentResource {
    private final StudentServiceImpl studentService;

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll(){
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>>getStudentById(@PathVariable String id){
        return this.studentService
                .getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.OK.toString())))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @PostMapping("/students")
    private Mono<ResponseEntity<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO){
        return this.studentService
                .saveStudent(studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> updateStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO){
        return this.studentService
                .updateStudent(id, studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(studentDTO, HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>>deleteStudent(@PathVariable String id){
        return this.studentService
                .deleteStudent(id)
                .map(s -> new ResponseEntity<>("Student with ID: was deleted", HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

    }
}
