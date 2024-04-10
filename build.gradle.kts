plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ir.syphix"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("com.github.Syrent:Origin:1.5.15")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.github.Slimefun:Slimefun4:RC-37")
}

tasks {

    processResources {
        filesMatching(listOf("**plugin.yml", "**plugin.json")) {
            expand(
                "version" to project.version as String,
                "name" to rootProject.name,
                "description" to project.description
            )
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
        archiveClassifier.set("")
        destinationDirectory.set(file(rootProject.projectDir.path + "/bin"))
        exclude("META-INF/**")
        from("LICENSE")
        minimize()
    }

    java {
        toolchain{
            languageVersion.set(JavaLanguageVersion.of(17))
        }
        withJavadocJar()
        withSourcesJar()
    }

    jar {
        enabled = false
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenBuild") {
            from(components["java"])
            pom {
                name.set("palladiumpool")
                description.set(rootProject.description)
                url.set("https://github.com/SyphiX897/PalladiumPool")
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://github.com/SyphiX897/PalladiumPool/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("syphix")
                        name.set("nima")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/syphix897/palladiumpool.git")
                    developerConnection.set("scm:git:ssh://github.com/syphix897/palladiumpool.git")
                    url.set("https://github.com/SyphiX897/PalladiumPool/tree/master")
                }
            }
        }
    }

    repositories {
        maven {
            name = "sayandevelopment-repo"
            url = uri("https://repo.sayandevelopment.org/snapshots/")

            credentials {
                username = project.findProperty("repo.sayan.user") as String
                password = project.findProperty("repo.sayan.token") as String
            }
        }
    }
}
