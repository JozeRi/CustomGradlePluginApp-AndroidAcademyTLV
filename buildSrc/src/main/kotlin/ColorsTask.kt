import extensions.writeXmlWithTags
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class ColorsTask : DefaultTask(){
    @get:OutputFile
    lateinit var outputFile: File

    @get:Input
    val colorsMap = mapOf(
        "main_color" to "#d32f2f",
        "secondary_color" to "#f44336",
        "third_color" to "#e57373",
        "fourth_color" to "#b71c1c"
    )

    @TaskAction
    fun makeResources() {
        colorsMap.entries.joinToString { (colorName, color) ->
            "\n    <color name=\"$colorName\">$color</color>"
        }.also { xml ->
            outputFile.writeXmlWithTags(xml)
        }
    }
}