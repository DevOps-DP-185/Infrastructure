package Simulation

import Simulation.buildTypes.*
import Simulation.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Simulation")
    name = "Simulation"

    vcsRoot(Simulation_vcs)

    buildType(Simulation_Build)
    buildType(Simulation_Deploy)
    buildType(Simulation_Database_Update)
    buildType(Simulation_Database_Rollback)   
})
