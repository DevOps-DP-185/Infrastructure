package Messaging

import Messaging.buildTypes.*
import Messaging.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Messaging")
    name = "Messaging"

    vcsRoot(Messaging_vcs)

    buildType(Messaging_Build)
    buildType(Messaging_Deploy)
    buildType(Messaging_Database_Update)
    buildType(Messaging_Database_Rollback)    
})
