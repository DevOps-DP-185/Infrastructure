package Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Identity_SonarQube : BuildType({
    name = "SonarQube"

    vcs {
        root(Identity.vcsRoots.Identity_vcs)
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
        snapshot(Identity_Build) {
            onDependencyCancel = FailureAction.CANCEL
        }
    }        
})