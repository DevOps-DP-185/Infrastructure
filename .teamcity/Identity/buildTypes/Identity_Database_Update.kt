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
        script {
            name = "Identity_Database_Update"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                cd ./identity_service/
                sudo mvn liquibase:update
            """.trimIndent()
        }
    }
})