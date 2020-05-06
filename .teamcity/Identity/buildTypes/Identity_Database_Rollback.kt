package Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Identity_Database_Rollback : BuildType({
    name = "DB_Rollback"

    vcs {
        root(Identity.vcsRoots.Identity_vcs)
    }

    steps {
        maven {
            name = "Identity_Database_Update"
            goals = "liquibase:rollback -Dliquibase.rollbackCount=1"
            pomLocation = "./identity-service/pom.xml"
        }
    }
})