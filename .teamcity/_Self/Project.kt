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

    params {
        param("env.ip_testing", "34.72.168.44")
        param("env.ip_staging", "172.0.0.1")
        param("env.username", "artemkulish123")
    }

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
