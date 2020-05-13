package _Self

import _Self.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry

object Project : Project({

    subProject(Discovery.Project) 
    subProject(Payment.Project) 
    subProject(Vehicle.Project) 
    subProject(Trip.Project) 
    subProject(Messaging.Project)
    subProject(Gateway.Project)
    subProject(Simulation.Project)
    subProject(Identity.Project)

    vcsRoot(Test_vcs)

    features {
        dockerRegistry {
            id = "Demo_4"
            name = "Docker Registry"
            url = "https://docker.io"
            userName = "artemkulish"
            password = "credentialsJSON:91a788d6-72b3-405f-a9df-03389f20d48c"
        }
    }
})
