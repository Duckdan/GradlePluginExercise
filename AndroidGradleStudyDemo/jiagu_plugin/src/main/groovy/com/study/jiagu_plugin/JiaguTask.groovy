package com.study.jiagu_plugin

import com.android.builder.model.SigningConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 加固的Task
 */
class JiaguTask extends DefaultTask {
    Jiagu jiagu
    SigningConfig signingConfig
    File apk

    JiaguTask() {
        //用于在侧边栏归集任务
        group = "jiagu"
    }

    @TaskAction
    def run() {
        //调用命令行工具
        project.exec {
            println "111Task::${apk.absolutePath}"
            it.commandLine("java", "-jar", jiagu.jiaguTools, "-login", jiagu.userName, jiagu.password)
        }

        //如果有签名配置
        if (signingConfig) {
            project.exec {
                println "222Task::${apk.absolutePath}"
                //导入签名信息
                it.commandLine("java", "-jar", jiagu.jiaguTools, "-importsign", signingConfig.storeFile.getAbsolutePath(),
                        signingConfig.storePassword, signingConfig.keyAlias, signingConfig.keyPassword)
            }
        }

        //自动签名
        project.exec {
            println "333Task::${apk.absolutePath}"
            // java -jar jiagu.jar -jiagu  xxxx
            it.commandLine("java", "-jar", jiagu.jiaguTools, "-jiagu", apk.absolutePath, apk.parent, "-autosign")
        }
    }
}
