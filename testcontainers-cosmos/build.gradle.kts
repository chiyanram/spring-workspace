plugins {
    id("springboot-conventions")
}


extra["springCloudAzureVersion"] = "5.20.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.azure.spring:spring-cloud-azure-starter-actuator")
    implementation("com.azure.spring:spring-cloud-azure-starter-data-cosmos")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("org.testcontainers:azure")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
    }
}
