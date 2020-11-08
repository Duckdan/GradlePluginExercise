

import com.android.builder.model.SigningConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class JiaGuTask extends DefaultTask {

    JiaGu jiaGu
    SigningConfig signingConfig
    File apk

    JiaGuTask() {
        //用于在侧边栏归集任务
        group = "jiagu"
    }

    @TaskAction
    def run() {
        //调用命令行工具
        project.exec {
            // java -jar jiagu.jar -login user password
            it.commandLine("java", "-jar", jiaGu.jiaGuToolsPath, "-login", jiaGu.username, jiaGu.password)
        }
        //非空即为true
        if (signingConfig) {
            project.exec {
                // java -jar jiagu.jar -importsign  xxxx
                it.commandLine("java", "-jar", jiaGu.jiaGuToolsPath, "-importsign", signingConfig.storeFile.absolutePath,
                        signingConfig.storePassword, signingConfig.keyAlias, signingConfig.keyPassword)
            }
        }
        project.exec {
            // java -jar jiagu.jar -jiagu  xxxx
            it.commandLine("java", "-jar", jiaGu.jiaGuToolsPath, "-jiagu", apk.absolutePath,
                    apk.parent, "-autosign")
        }
    }
}