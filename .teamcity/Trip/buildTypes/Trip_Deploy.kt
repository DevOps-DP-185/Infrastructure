package Trip.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Trip_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Trip.vcsRoots.Trip_vcs)
    }

    steps {
        script {
            name = "Transfer env application files"
            scriptContent = "scp -r /home/env %env.username%@%env.ip_staging%:/tmp"
        }
        step {
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.deployer.username", "%env.username%")
            param("jetbrains.buildServer.sshexec.command", """
                sudo cp -r /tmp/env /var
                cd /var && sudo bash deploy.sh
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "%env.ip_staging%")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.sshexec.keyFile", "/home/artemkulish123/")
        }
    }

    dependencies {
        snapshot(Trip_Test) {
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
