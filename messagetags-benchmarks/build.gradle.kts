plugins {
    id("me.champeau.jmh") version "0.7.2"
}

dependencies {
    // Source: https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.37")

    jmh("com.hypixel.hytale:Server:2026.02.06-aa1b071c2")
    jmh(project(":messagetags-api"))
    jmh(files("libs/tinymessage-2.0.1.jar"))
    jmh(files("libs/TaleMessage-1.0.2.jar"))
}

sourceSets {
    main {
        java.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
    test {
        java.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
}

jmh {
    jvmArgs.add("--enable-native-access=ALL-UNNAMED")
    profilers.add("gc")
    profilers.add("stack")

    // Switch based on your needs
    //resultFormat = "TEXT"
    resultFormat = "JSON"

    // Not working on windows
    //profilers.add("async")
    //profilers.add("compiler")
}