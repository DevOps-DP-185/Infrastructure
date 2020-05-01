import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

version = "2019.2"

project {
    vcsRoot(Something)
    vcsRoot(Discovery)
    vcsRoot(Gateway)
}

object Something : GitVcsRoot({
    name = "Something"
    url = "https://github.com/DevOps-DP-185/Ansible.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})

object Discovery : GitVcsRoot({
    name = "Discovery"
    url = "https://github.com/DevOps-DP-185/Discovery.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})

object Gateway : GitVcsRoot({
    name = "Gateway"
    url = "https://github.com/DevOps-DP-185/Gateway.git"
    authMethod = password {
        userName = "ArtemKulish"
        password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
    }
})
