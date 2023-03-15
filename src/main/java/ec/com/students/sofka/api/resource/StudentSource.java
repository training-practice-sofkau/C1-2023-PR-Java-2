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
public class StudentSource {

    private final StudentServiceImpl studentService;

    @GetMapping("/students")
    private Mono<ResponseEntity<Flux<StudentDTO>>> getAll(){
        return this.studentService
                .getAllStudents()
                .switchIfEmpty(Flux.error(new Throwable()))
                .collectList()
                .map(studentDTOS -> new ResponseEntity<>(Flux.fromIterable(studentDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> getById(@PathVariable String id){
        return this.studentService.getStudentById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(bookDTO -> new ResponseEntity<>(bookDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }

    @PostMapping("/students")
    private Mono<ResponseEntity<StudentDTO>> save(@RequestBody StudentDTO bookDTO){
        return this.studentService
                .saveStudent(bookDTO)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(bookDTO1 -> new ResponseEntity<>(bookDTO1,HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));
    }

    @PutMapping("/students/{id}")
    private Mono<ResponseEntity<StudentDTO>> update(@PathVariable String id, @RequestBody StudentDTO bookDTO){
        return this.studentService
                .updateStudent(id, bookDTO)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(bookDTO1 -> new ResponseEntity<>(bookDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/students/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return this.studentService
                .deleteStudent(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(s -> new ResponseEntity<>("Deleted "+s,HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

}
