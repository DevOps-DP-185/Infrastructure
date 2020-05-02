package Discovery

import Discovery.buildTypes.*
import Discovery_vcs.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Discovery")
    name = "Discovery"

    vcsRoot(Discovery_vcs)

    buildType(Discovery_Deploy)
    buildType(Discovery_Build)
})
