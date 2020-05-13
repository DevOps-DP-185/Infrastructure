package Gateway

import Gateway.buildTypes.*
import Gateway.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Gateway")
    name = "Gateway"

    vcsRoot(Gateway_vcs)

    buildType(Gateway_Build)
    buildType(Gateway_Deploy)
    buildType(Gateway_Test)
})
