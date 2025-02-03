package com.rmurugaian.udemy;

import org.springframework.boot.SpringApplication;

public class TestUdemyWebfluxApplication {

    public static void main(final String[] args) {
        SpringApplication.from(UdemyWebfluxApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
