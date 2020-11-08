package com.study.jiagu_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.builder.model.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 插件的实现需要继承Plugin
 */
class JiaguPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //创建jiagu插件
        Jiagu jiagu = project.extensions.create("jiagu", Jiagu)//Jiagu是Jiagu.class的简写
        //回调，在gradle配置完成之后回调，在解析完build.gradle之后回调
        project.afterEvaluate {
            //因为app/build.gradle中与android{}闭包相关联的类时AppExtension
            AppExtension android = project.extensions.android
            //获取当前应用打包的变种
            android.applicationVariants.all {
                ApplicationVariant variant ->
                    //对应build.gradle中release/debug的签名配置信息
                    SigningConfig signingConfig = variant.signingConfig
                    variant.outputs.all {
                        BaseVariantOutput output ->
                            //apk输出的位置
                            File outputFile = output.outputFile
                            println "当前apk地址：：${outputFile.getAbsolutePath()}"
                            //创建加固任务  jiag  Debug capitalize()方法首字母大写
                            JiaguTask jiaguTask = project.tasks.create("jiagu${variant.baseName.capitalize()}", JiaguTask)
                            jiaguTask.jiagu = jiagu
                            jiaguTask.signingConfig = signingConfig
                            jiaguTask.apk = outputFile
                    }
            }
        }
    }
}