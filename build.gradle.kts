// === PLUGINS =====================================================================================

plugins {
    java
    idea
}

// === MAIN BUILD DETAILS ==========================================================================

group = "com.norswap"
version = "1.0.0-ALPHA" // not -SNAPSHOT because Bintray is boneheaded and forbids it
description = "Language implementation demo"
java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

val website = "https://github.com/norswap/${project.name}"
val vcs = "https://github.com/norswap/${project.name}.git"

sourceSets.main.get().java.srcDir("src")
sourceSets.test.get().java.srcDir("test")

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test.get().useTestNG()

tasks.javadoc.get().options {
    // https://github.com/gradle/gradle/issues/7038
    this as StandardJavadocDocletOptions
    addStringOption("Xdoclint:none", "-quiet")
    if (JavaVersion.current().isJava9Compatible)
        addBooleanOption("html5", true) // nice future proofing
}

tasks.withType(JavaCompile::class) {
    // Give unsafe casts details instead of passive agressively hinting that they exist.
    options.compilerArgs.plusAssign("-Xlint:unchecked")
    options.isDeprecation = true
}

// === IDE =========================================================================================

idea.module {
    // Download javadoc & sources for dependencies.
    isDownloadJavadoc = true
    isDownloadSources = true
}

// === DEPENDENCIES ================================================================================

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.norswap:utils:2.0.2")
    implementation("com.norswap:autumn:1.0.2-ALPHA")
    implementation("com.norswap:uranium:1.0.3-ALPHA")
    testImplementation("org.testng:testng:6.14.3")
}

// =================================================================================================