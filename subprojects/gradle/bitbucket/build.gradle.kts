plugins {
    id("convention.kotlin-jvm")
    id("convention.publish-kotlin-library")
    id("convention.gradle-testing")
}

dependencies {
    implementation(gradleApi())

    implementation(projects.subprojects.common.okhttp)
    implementation(projects.subprojects.gradle.buildEnvironment)
    implementation(projects.subprojects.gradle.git)
    implementation(projects.subprojects.gradle.gradleExtensions)
    implementation(projects.subprojects.gradle.impactShared)
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.okhttpLogging)
    implementation(libs.sentry)

    gradleTestImplementation(projects.subprojects.gradle.testProject)
    gradleTestImplementation(projects.subprojects.common.testOkhttp)
    gradleTestImplementation(projects.subprojects.common.truthExtensions)
}
