package Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Simulation_Database_Rollback : BuildType({
    name = "DB_Rollback"

    vcs {
        root(Simulation.vcsRoots.Simulation_vcs)
    }

    steps {
        script {
            name = "Simulation_Database_Rollback"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                sudo mvn liquibase:rollback -Dliquibase.rollbackCount=1
            """.trimIndent()
        }
    }
})