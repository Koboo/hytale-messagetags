plugins {
    id("java-library")
    id("com.gradleup.shadow") version("9.3.1")
    id("maven-publish")
    id("com.vanniktech.maven.publish") version("0.35.0")
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")

    if(name != "messagetags-benchmarks") {
        apply(plugin = "maven-publish")
        apply(plugin = "com.vanniktech.maven.publish")
    }

    group = "eu.koboo"
    version = "1.2.0"

    repositories {
        maven {
            name = "hytale-release"
            url = uri("https://maven.hytale.com/release")
        }
        maven {
            name = "entixReposilite"
            url = uri("https://repo.entix.eu/releases")
        }
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compileOnly("com.hypixel.hytale:Server:2026.02.06-aa1b071c2")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
        javadoc {
            options.encoding = "UTF-8"
            (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
        }
    }

    if(name != "messagetags-benchmarks") {
        mavenPublishing {
            mavenPublishing {
                coordinates(
                    project.group.toString(),
                    project.name.toString(),
                    project.version.toString()
                )

                pom {
                    name.set("MessageTags")
                    description.set("Color and format parser for Hytale messages")
                    inceptionYear.set("2026")
                    url.set("https://github.com/Koboo/hytale-messagetags")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://mit-license.org/")
                            distribution.set("https://mit-license.org/")
                        }
                    }
                    developers {
                        developer {
                            id.set("koboo")
                            name.set("Koboo")
                            url.set("https://github.com/Koboo")
                        }
                    }
                    scm {
                        url.set("https://github.com/Koboo/hytale-messagetags")
                        connection.set("scm:git:git:github.com/Koboo/hytale-messagetags.git")
                        developerConnection.set("scm:git:ssh://git@github.com/Koboo/hytale-messagetags.git")
                    }
                }
            }
            publishToMavenCentral()
            signAllPublications()
        }

        publishing {
            repositories {
                mavenLocal()
                maven {
                    name = "entixReposiliteReleases"
                    url = uri("https://repo.entix.eu/releases")
                    credentials {
                        username = System.getenv("ENTIX_REPO_USER")
                        password = System.getenv("ENTIX_REPO_PASS")
                    }
                }
                maven {
                    name = "entixReposiliteSnapshots"
                    url = uri("https://repo.entix.eu/snapshots")
                    credentials {
                        username = System.getenv("ENTIX_REPO_USER")
                        password = System.getenv("ENTIX_REPO_PASS")
                    }
                }
            }
        }
    }
}