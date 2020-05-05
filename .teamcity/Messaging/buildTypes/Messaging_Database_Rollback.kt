package Messaging.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Messaging_Database_Rollback : BuildType({
    name = "DB_Rollback"

    vcs {
        root(Messaging.vcsRoots.Messaging_vcs)
    }

    steps {
        script {
            name = "Messaging_Database_Rollback"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:rollback -Dliquibase.rollbackCount=1
            """.trimIndent()
        }
    }
})