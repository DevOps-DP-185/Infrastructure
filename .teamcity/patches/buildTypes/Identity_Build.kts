package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Identity_Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Identity_Build")) {
    expectSteps {
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        script {
            name = "Add sumo_credentials.txt"
            scriptContent = "cp /home/sumo_credentials.txt ./"
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "artemkulish/demo4:identity"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            commandType = push {
                namesAndTags = "artemkulish/demo4:identity"
            }
            param("dockerfile.path", "Dockerfile")
        }
    }
    steps {
        update<MavenBuildStep>(0) {
            name = "Starter Build"
            goals = "clean install"
            pomLocation = "./identity-starter/pom.xml"
            runnerArgs = ""
            localRepoScope = MavenBuildStep.RepositoryScope.AGENT
            jdkHome = ""
        }
        insert(1) {
            maven {
                name = "Identity Build"
                goals = "clean package"
                pomLocation = "./identity-service/pom.xml"
            }
        }
        update<ScriptBuildStep>(2) {
            scriptContent = "cp /home/sumo_credentials.txt ./identity-service/"
        }
        update<DockerCommandStep>(3) {
            name = "Docker Build"
            commandType = build {
                source = file {
                    path = "identity-service/Dockerfile"
                }
                contextDir = ""
                namesAndTags = "artemkulish/demo4:identity"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "")
        }
        update<DockerCommandStep>(4) {
            name = "Docker Push"
            commandType = push {
                namesAndTags = "artem"
                removeImageAfterPush = true
            }
            param("dockerfile.path", "identity-service/Dockerfile")
        }
    }
}
