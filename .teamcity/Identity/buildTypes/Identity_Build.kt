package Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Identity_Build : BuildType({
    name = "Build"

    vcs {
        root(Identity.vcsRoots.Identity_vcs)
    }

steps {
    maven {
        name = "Starter Build"
        goals = "clean install"
        pomLocation = "./identity-starter/pom.xml"
    }
    maven {
        name = "Identity Build"
        goals = "clean package"
        pomLocation = "./identity-service/pom.xml"
    }
    script {
        name = "Add sumo_credentials.txt"
        scriptContent = "cp /home/sumo_credentials.txt ./identity-service/"
    }
    dockerCommand {
        name = "Docker Build"
        commandType = build {
            source = file {
                path = "identity-service/Dockerfile"
            }
            namesAndTags = "artemkulish/demo4:identity"
        }
    }
    dockerCommand {
        name = "Docker Push"
        commandType = push {
            namesAndTags = "artemkulish/demo4:identity"
        }
        param("dockerfile.path", "identity-service/Dockerfile")
    }
}

    triggers {
        vcs {
        }
    }

    features {
        dockerSupport {
            loginToRegistry = on {
                dockerRegistryId = "Demo_4"
            }
        }
    }
})
