package Gateway.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Gateway_vcs : GitVcsRoot({
    name = "Gateway_vcs"
    url = "https://github.com/DevOps-DP-185/Gateway.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})
