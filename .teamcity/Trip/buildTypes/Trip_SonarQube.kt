package Trip.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Trip_SonarQube : BuildType({
    name = "SonarQube"

    vcs {
        root(Trip.vcsRoots.Trip_vcs)
    }

    steps {
        maven {
            goals = "clean verify sonar:sonar"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
    }

    triggers {
        vcs {
        }
    }
    
    dependencies {
        snapshot(Trip_Build) {
            onDependencyCancel = FailureAction.CANCEL
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