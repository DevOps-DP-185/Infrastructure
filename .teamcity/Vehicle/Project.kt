package Vehicle

import Vehicle.buildTypes.*
import Vehicle.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Vehicle")
    name = "Vehicle"

    vcsRoot(Vehicle_vcs)

    buildType(Vehicle_Build)
    buildType(Vehicle_Deploy)
    buildType(Vehicle_Test)
    buildType(Vehicle_Database_Update)
    buildType(Vehicle_Database_Rollback)    
})
