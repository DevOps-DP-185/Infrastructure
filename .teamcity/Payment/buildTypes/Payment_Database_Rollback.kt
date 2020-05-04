package Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Payment_Database_Rollback : BuildType({
    name = "Database_Rollback"

    vcs {
        root(Payment.vcsRoots.Payment_vcs)
    }

    steps {
        script {
            name = "Payment_Database_Rollback"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:rollback -Dliquibase.rollbackCount=1
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})