package Gateway.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Gateway_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Gateway.vcsRoots.Gateway_vcs)
    }

    steps {
        step {
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.sshexec.command", "ls")
            param("jetbrains.buildServer.deployer.targetUrl", "35.184.252.223")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.sshexec.keyFile", "/home/artemkulish123/")
        }
    }

    triggers {
        vcs {
        }
    }

    dependencies {
        snapshot(Gateway_Build) {
        }
    }
})