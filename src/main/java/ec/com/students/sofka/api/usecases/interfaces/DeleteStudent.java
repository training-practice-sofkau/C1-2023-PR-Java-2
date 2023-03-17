package ec.com.students.sofka.api.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteStudent {

    Mono<Void> delete(String id);
}
