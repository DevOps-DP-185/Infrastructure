package Payment

import Payment.buildTypes.*
import Payment.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Payment")
    name = "Payment"

    vcsRoot(Payment_vcs)

    buildType(Payment_Build)
    buildType(Payment_Deploy)
    buildType(Payment_Database_Update)
    buildType(Payment_Database_Rollback)
})
