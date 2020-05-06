package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Identity_Database_Rollback'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Identity_Database_Rollback")) {
    expectSteps {
        script {
            name = "Identity_Database_Rollback"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            scriptContent = """
                cd ./identity_service/
                sudo mvn liquibase:rollback -Dliquibase.rollbackCount=1
            """.trimIndent()
        }
    }
    steps {
        insert(0) {
            maven {
                name = "Identity_Database_Rollback"
                goals = "liquibase:rollback -Dliquibase.rollbackCount=1"
                pomLocation = "./identity-service/pom.xml"
            }
        }
        items.removeAt(1)
    }
}
