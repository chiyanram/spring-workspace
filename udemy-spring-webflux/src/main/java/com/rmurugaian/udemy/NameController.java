package com.rmurugaian.udemy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
class NameController {

    @GetMapping("/name")
    Mono<String> getName() {
        return Mono.just("Hello World")
                .doOnNext(name -> log.info("Name: {}", name));
    }
}
