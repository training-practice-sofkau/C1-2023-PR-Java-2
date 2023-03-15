package ec.com.students.sofka.api.resource;

import ec.com.students.sofka.api.domain.collection.Student;
import ec.com.students.sofka.api.domain.dto.StudentDTO;
import ec.com.students.sofka.api.service.impl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentResource {

    private final StudentServiceImpl studentService;

    @GetMapping("")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll(){
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/{id}")
    private Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id){
        return this.studentService.getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("")
    private Mono<ResponseEntity<StudentDTO>> save (@Valid @RequestBody StudentDTO studentDTO){
        return this.studentService
                .saveStudent(studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/{id}")
    private Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @Valid @RequestBody StudentDTO studentDTO){
        return this.studentService
                .updateStudent(id, studentDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(studentDTO1 -> new ResponseEntity<>(studentDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(studentDTO, HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return this.studentService
                .deleteStudent(id)
                .map(s -> new ResponseEntity<>("Deleted " + s, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
