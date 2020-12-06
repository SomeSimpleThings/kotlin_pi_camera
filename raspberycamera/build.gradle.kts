repositories {
    mavenCentral()
}

dependencies {
    implementation("uk.co.caprica:picam:2.0.2")
}

tasks.withType<Jar>() {
    manifest {
        attributes["Main-Class"] = "com.papuguys.camera.raspcamera.MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}


