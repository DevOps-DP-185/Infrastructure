package Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Identity_Database_Update : BuildType({
    name = "DB_Update"

    vcs {
        root(Identity.vcsRoots.Identity_vcs)
    }

    steps {
        maven {
            name = "Identity_Database_Update"
            goals = "liquibase:update"
            pomLocation = "./identity-service/pom.xml"
        }
    }
})