package uz.leeway.plugins.version

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

@Suppress("unused")
class VersionCheckPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.github.ben-manes.versions")

            tasks.withType<DependencyUpdatesTask> {
                rejectVersionIf {
                    candidate.version.isNonStable() && !currentVersion.isNonStable()
                }
                checkForGradleUpdate = true
                outputFormatter = "text"
                reportfileName = "report"
                outputDir = project.rootProject.layout.buildDirectory
                    .dir("reports/dependency-updates")
                    .get().asFile.absolutePath
            }
        }
    }

    private fun String.isNonStable(): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(this)
        return isStable.not()
    }
}