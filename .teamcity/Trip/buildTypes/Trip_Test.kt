package Trip.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Trip_Test : BuildType({
    name = "Test"

    vcs {
        root(_Self.vcsRoots.Test_vcs)
    }

    steps {
        step {
            name = "Deploy to Test environment"
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.deployer.username", "artemkulish123")
            param("jetbrains.buildServer.sshexec.command", """
                cd /var/
                sudo bash ./deploy.sh
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "34.72.168.44")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
        }
        maven {
            name = "Test"
            goals = "-Dtest=RunScooterTests test"
            jdkHome = "%env.JDK_11%"
        }
    }

    dependencies {
        snapshot(Trip_Build) {
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
