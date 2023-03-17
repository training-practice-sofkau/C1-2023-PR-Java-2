package ec.com.students.sofka.api.useCases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteStudent {
    Mono<Void> delete(String id);
}
