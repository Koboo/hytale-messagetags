dependencies {
    val jupiterVersion = "6.0.3"
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$jupiterVersion")

    // We need hytale on test scope
    testImplementation("com.hypixel.hytale:Server:2026.02.06-aa1b071c2")

    // https://central.sonatype.com/artifact/com.google.code.gson/gson
    testImplementation("com.google.code.gson:gson:2.13.2")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src"))
        resources.setSrcDirs(emptyList<String>())
    }
    test {
        java.setSrcDirs(listOf("test"))
        resources.setSrcDirs(emptyList<String>())
    }
}