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
        step {
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.sshexec.command", "ls")
            param("jetbrains.buildServer.deployer.targetUrl", "10.128.0.9")
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
