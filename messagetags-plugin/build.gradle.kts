dependencies {
    api(project(":messagetags-api"))
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src/java"))
        resources.setSrcDirs(listOf("src/resources"))
    }
    test {
        java.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
}