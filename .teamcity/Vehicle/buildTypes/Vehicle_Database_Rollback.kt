package Vehicle.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Vehicle_Database_Rollback : BuildType({
    name = "DB_Rollback"

    vcs {
        root(Vehicle.vcsRoots.Vehicle_vcs)
    }

    steps {
        script {
            name = "Vehicle_Database_Rollback"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:rollback -Dliquibase.rollbackCount=1
            """.trimIndent()
        }
    }
})