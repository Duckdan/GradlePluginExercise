import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.builder.model.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 不配置resources时，可以直接在app/build.gradle中引用JiaGuPlugin
 */
class JiaGuPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        JiaGu jiagu = project.extensions.create("jiagu", JiaGu)
        //回调，在gradle配置完成之后回调，在解析完build.gradle之后回调
        project.afterEvaluate {
            AppExtension android = project.extensions.android
            android.applicationVariants.all {
                ApplicationVariant variant ->
                    SigningConfig signingConfig = variant.signingConfig
                    variant.outputs.all {
                        BaseVariantOutput output ->
                            //输出的apk文件
                            File apk = output.outputFile
                            //创建加固任务
                            JiaGuTask jiaGuTask = project.tasks.create("jiagu${variant.baseName.capitalize()}", JiaGuTask)
                            jiaGuTask.jiaGu = jiagu
                            jiaGuTask.signingConfig = signingConfig
                            jiaGuTask.apk = apk
                    }
            }
        }
    }
}