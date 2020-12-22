import extensions.writeXmlWithTags
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

internal open class StringsTask : DefaultTask(){
    @get:OutputFile
    lateinit var outputFile: File

    @get:Input
    val stringsMap = mapOf(
        "main_string" to "MAIN",
        "secondary_string" to "SECONDARY",
        "third_string" to "THIRD",
        "fourth_string" to "FOURTH"
    )

    @TaskAction
    fun makeResources() {
        stringsMap.entries.joinToString { (stringName, string) ->
            "\n    <string name=\"$stringName\">$string</string>"
        }.also { xml ->
            outputFile.writeXmlWithTags(xml)
        }
    }
}