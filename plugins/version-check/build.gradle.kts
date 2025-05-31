plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1"
}

group = "uz.leeway.android.plugins"
version = "0.0.1"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.plugins.version.check.toDep())
}

private fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

gradlePlugin {
    website = "https://github.com/leeway-corp/android-plugins"
    vcsUrl = "https://github.com/leeway-corp/android-plugins"
    plugins {
        create("versionCheck") {
            id = "uz.leeway.android.plugins.version-check"
            displayName = "Version Check Plugin"
            description = "A plugin to check the version of the gradle dependencies"
            implementationClass = "uz.leeway.plugins.version.VersionCheckPlugin"
            tags.set(listOf("android", "version", "dependencies", "plugin"))
        }
    }
}
