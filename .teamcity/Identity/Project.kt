package Identity

import Identity.buildTypes.*
import Identity.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Identity")
    name = "Identity"

    vcsRoot(Identity_vcs)

    buildType(Identity_Build)
    buildType(Identity_Deploy)
    buildType(Identity_Test)
    buildType(Identity_Database_Update)
    buildType(Identity_Database_Rollback)    
})