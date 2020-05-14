package Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Payment_Test : BuildType({
    name = "Test"

    vcs {
        root(_Self.vcsRoots.Test_vcs)
    }

    steps {
        step {
            name = "Deploy to Test environment"
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.deployer.username", "%env.username%")
            param("jetbrains.buildServer.sshexec.command", """
                cd /var/
                sudo bash ./deploy.sh
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "%env.ip_testing%")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
        }
        maven {
            name = "Test"
            goals = "-Dtest=RunScooterTests test"
            jdkHome = "%env.JDK_11%"
        }
    }

    dependencies {
        snapshot(Payment_Build) {
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
