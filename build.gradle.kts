plugins {
    id("java")
    // This plugin creates a task named "shadowJar".
    // It puts/includes/shades/shadows (basically every previous word describes the same thing)
    // all your dependencies marked with "implementation" into your final JAR file.
    // JAR files created with shadow have the suffix "-all".
    // So if you build this project with "shadowJar" the JAR file name will be:
    // "hytale-plugin-template-1.0.0-all.jar"
    id("com.gradleup.shadow") version("9.3.1")
    id("eu.koboo.pluginmanifest") version("1.0.24")
}

group = "eu.koboo"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "entixReposilite"
        url = uri("https://repo.entix.eu/releases")
    }
}

dependencies {
    // Here you can add your own dependencies.
}

pluginManifest {
    // Nothing to configure.
    // The plugin already does most of the things for us. :)
    // https://github.com/Koboo/hytale-pluginmanifest

    manifestConfiguration {
        pluginGroup = "Koboo"
        pluginName = "TemplatePlugin"
    }

    runtimeConfiguration {
        runtimeDirectory = "D:/PluginManifestRuntime"
    }
}

java {
    // Sets gradle's used java version to "Java 25"
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    withSourcesJar()
    withJavadocJar()
}

tasks {
    compileJava {
        // Sets the compiling charset of your JAR to "UTF-8"
        options.encoding = "UTF-8"
    }
    javadoc {
        // Sets the compiling charset of your Javadocs to "UTF-8"
        options.encoding = "UTF-8"
        // Comment out this line to enable Javadoc warnings.
        // NOTE: This will probably spam your build-messages.
        (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src/java"))
        resources.setSrcDirs(listOf("src/resources"))
    }
    // We don't need test sources, we test on the server itself.
    test {
        java.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
}