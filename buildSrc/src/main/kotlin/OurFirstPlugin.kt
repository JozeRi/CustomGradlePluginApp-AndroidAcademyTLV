import com.android.build.gradle.api.BaseVariant
import extensions.android
import extensions.variants
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class OurFirstPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // The path of our generated resources
        val outputPath = "${project.buildDir}/generated/res"

        project.android().variants().all { variant ->

            generateColorsTask(outputPath, project, variant)

        }
    }

    private fun generateColorsTask(outputPath: String, project: Project, variant: BaseVariant) {
        // Make a task for each combination of build type and product flavor
        val colorTaskName = "generateColors${variant.name.capitalize()}"

        // Register the task. Gradle will parse and add the tasks for us for the given names.
        project.tasks.register(colorTaskName, ColorsTask::class.java) { colorTask ->
            colorTask.group = "OurNewPluginTasks"

            // We write our output in the build folder. Also note that we keep a
            // reference to this so as to later mark it as a generated resource folder
            val outputDirectory =
                File("$outputPath/${variant.dirName}").apply { mkdir() }
            colorTask.outputFile = File(outputDirectory, "values/generated_colors.xml")

            // Marks the output directory as an app resource folder
            variant.registerGeneratedResFolders(
                project.files(outputDirectory).builtBy(
                    colorTask
                )
            )
        }
    }
}