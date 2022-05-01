package com.avito.android.build_checks

import com.avito.android.build_checks.RootProjectChecksExtension.RootProjectCheck.AndroidSdk
import com.avito.android.build_checks.RootProjectChecksExtension.RootProjectCheck.GradleProperties
import com.avito.android.build_checks.RootProjectChecksExtension.RootProjectCheck.MacOSLocalhost
import com.avito.android.build_checks.RootProjectChecksExtension.RootProjectCheck.PreventKotlinDaemonFallback
import org.gradle.api.Action
import java.io.Serializable
import kotlin.reflect.full.createInstance

public open class RootProjectChecksExtension : BuildChecksExtension() {

    override val allChecks: List<Check>
        get() {
            return RootProjectCheck::class.sealedSubclasses
                .map { it.createInstance() }
        }

    @Suppress("DEPRECATION")
    @Deprecated(javaVersionCheckDeprecationMessage)
    public fun javaVersion(action: Action<RootProjectCheck.JavaVersion>): Unit =
        register(RootProjectCheck.JavaVersion(), action)

    public fun androidSdk(action: Action<AndroidSdk>): Unit =
        register(AndroidSdk(), action)

    public fun macOSLocalhost(action: Action<MacOSLocalhost>): Unit =
        register(MacOSLocalhost(), action)

    public fun gradleProperties(action: Action<GradleProperties>): Unit =
        register(GradleProperties(), action)

    public fun preventKotlinDaemonFallback(action: Action<PreventKotlinDaemonFallback>): Unit =
        register(PreventKotlinDaemonFallback(), action)

    public sealed class RootProjectCheck : Check {

        public override var enabled: Boolean = true

        public open class AndroidSdk : RootProjectCheck(), RequireValidation {

            public data class AndroidSdkVersion(
                val compileSdkVersion: Int,
                val revision: Int,
            ) : Serializable

            internal val versions = mutableSetOf<AndroidSdkVersion>()

            public fun version(compileSdkVersion: Int, revision: Int) {
                versions.add(
                    AndroidSdkVersion(compileSdkVersion, revision)
                )
            }

            override fun validate() {
                require(versions.isNotEmpty()) {
                    "At least one version must be configured in buildChecks.androidSdk"
                }
            }
        }

        @Deprecated(javaVersionCheckDeprecationMessage)
        public open class JavaVersion : RootProjectCheck(), RequireValidation {

            override var enabled: Boolean = false

            public var version: org.gradle.api.JavaVersion? = null

            override fun validate() {
                checkNotNull(version) { "$extensionName.javaVersion.version must be set" }
            }
        }

        public open class MacOSLocalhost : RootProjectCheck()

        public open class GradleProperties : RootProjectCheck() {
            override var enabled: Boolean = false
        }

        public open class PreventKotlinDaemonFallback : RootProjectCheck() {
            override var enabled: Boolean = true
        }

        override fun equals(other: Any?): Boolean {
            return this.javaClass == other?.javaClass
        }
    }
}

internal const val javaVersionCheckDeprecationMessage = """
Java version build check is deprecated and will be deleted. 
It's too late to make it even in configuration phase.
You can check the Java version in your settings.gradle instead.
"""
