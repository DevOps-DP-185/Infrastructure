package Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Payment_Database_Update : BuildType({
    name = "Database_Update"

    vcs {
        root(Payment.vcsRoots.Payment_vcs)
    }

    steps {
        script {
            name = "Payment_Database_Update"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:update
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})