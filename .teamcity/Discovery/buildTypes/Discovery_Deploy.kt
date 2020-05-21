package Discovery.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Discovery_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Discovery.vcsRoots.Discovery_vcs)
    }

    steps {
        step {
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.deployer.username", "%env.username%")
            param("jetbrains.buildServer.sshexec.command", """
                cd /var && sudo bash deploy.sh
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "%env.ip_staging%")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.sshexec.keyFile", "/home/artemkulish123/")
        }
    }

    triggers {
        vcs {
        }
    }
    
    dependencies {
        snapshot(Discovery_SonarQube) {
            onDependencyCancel = FailureAction.CANCEL
        }
        snapshot(Discovery_Test) {
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
