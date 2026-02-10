dependencies {
    val jupiterVersion = "6.0.2"
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$jupiterVersion")
}

tasks.test {
    useJUnitPlatform()
}

pluginManifest {
    isServerPlugin = false
}

sourceSets {
    test {
        java.setSrcDirs(listOf("src/test"))
        resources.setSrcDirs(emptyList<String>())
    }
}