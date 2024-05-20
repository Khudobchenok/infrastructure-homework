dependencies {
    implementation(project(":businessPeople"))

    implementation("javax.inject:javax.inject:1")
    implementation("org.springframework:spring-web")

    // tests
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}