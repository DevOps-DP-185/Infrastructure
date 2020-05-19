package Trip

import Trip.buildTypes.*
import Trip.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Trip")
    name = "Trip"

    vcsRoot(Trip_vcs)

    buildType(Trip_Build)
    buildType(Trip_Deploy)
    buildType(Trip_Test)
    buildType(Trip_SonarQube) 
    buildType(Trip_Database_Update)
    buildType(Trip_Database_Rollback)      
})
