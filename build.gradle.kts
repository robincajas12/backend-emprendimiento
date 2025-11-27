plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.project007"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // CDI (Weld SE)
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
    // Opcional: Jandex (útil si haces scanning)
    implementation("io.smallrye:jandex:3.2.3")
    implementation("org.projectlombok:lombok:1.18.42")

    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")

    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("org.hibernate.orm:hibernate-core:7.2.0.CR3")


    implementation("org.postgresql:postgresql:42.7.8")

}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.test {
    useJUnitPlatform()
}