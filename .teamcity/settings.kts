import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

version = "2019.2"

project {
    vcsRoot(Something)
    vcsRoot(Discovery)
}

object Something : GitVcsRoot({
    name = "Something"
    url = "https://github.com/DevOps-DP-185/Ansible.git"
})

object Discovery : GitVcsRoot({
    name = "Discovery"
    url = "https://github.com/DevOps-DP-185/Discovery.git"
})
