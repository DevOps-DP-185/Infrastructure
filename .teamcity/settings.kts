import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

version = "2019.2"

project {
    vcsRoot(Something)
}

object Something : GitVcsRoot({
    name = "Something"
    url = "https://github.com/DevOps-DP-185/Ansible.git"
})
