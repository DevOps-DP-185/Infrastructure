package Messaging.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Messaging_Database_Update : BuildType({
    name = "DB_Update"

    vcs {
        root(Messaging.vcsRoots.Messaging_vcs)
    }

    steps {
        script {
            name = "Messaging_Database_Update"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:update
            """.trimIndent()
        }
    }
})