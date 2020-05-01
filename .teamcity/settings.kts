import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

version = "2019.2"

project {
    subProject(Test) 
    subProject(Test_1)
    subProject(Test_2)
    
    features {
        dockerRegistry {
            id = "Demo_4"
            name = "Docker Registry"
            url = "https://docker.io"
            userName = "artemkulish"
            password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
        }
    }
}

/*******************************************
*-------------SUB_PROJECTS-----------------*
*******************************************/

object Test : Project({
    name = "Test"
    
    vcsRoot(Gateway)
    buildType(Test_Build)
})

object Test_1 : Project({
    name = "Test_1"
    
    vcsRoot(Payment)
    buildType(Payment)
})

object Test_2 : Project({
    name = "Test_2"
    
    vcsRoot(Discovery)
    buildType(Discovery)
})

/******************************************
*----------------BUILD--------------------*
******************************************/

object Test_Build : BuildType({
    name = "Build"

    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(Gateway)
    }

    steps {
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = bundled_3_1()
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "artemkulish/demo4:gateway"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            commandType = push {
                namesAndTags = "artemkulish/demo4:gateway"
            }
            param("dockerfile.path", "Dockerfile")
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

object Payment : BuildType({
    name = "Build"

    vcs {
        root(Payment)
    }

    steps {
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "artemkulish/demo4:payment"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            commandType = push {
                namesAndTags = "artemkulish/demo4:payment"
            }
            param("dockerfile.path", "Dockerfile")
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

object Discovery : BuildType({
    name = "Build"

    vcs {
        root(Discovery)
    }

    steps {
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "artemkulish/demo4:discovery"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            commandType = push {
                namesAndTags = "artemkulish/demo4:discovery"
            }
            param("dockerfile.path", "Dockerfile")
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

/******************************************
*--------------WEB_HOOKS------------------*
******************************************/

object Gateway : GitVcsRoot({
    name = "Gateway"
    url = "https://github.com/DevOps-DP-185/Gateway.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})

object Payment : GitVcsRoot({
    name = "Payment"
    url = "https://github.com/DevOps-DP-185/Payment.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})

object Discovery : GitVcsRoot({
    name = "Discovery"
    url = "https://github.com/DevOps-DP-185/Discovery.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})
