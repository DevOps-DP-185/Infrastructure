resource "null_resource" "create_vmss" {
  provisioner "local-exec" {
  command = "az vmss create  -n MyVmss -g demo \
--image /subscriptions/92c6b1c1-2921-4a64-b4dc-dd0f92882bf6/resourceGroups/demo/providers/Microsoft.Compute/images/image \
--instance-count 1 \
--vm-sku Standard_D2s_v3 \
--vnet-name AGVNET \
--subnet Sub \
--app-gateway example-appgateway"
--admin-username grishenkovitali
  }
}

resource "null_resource" "autoscale_create" {
  provisioner "local-exec" {
  command = "az monitor autoscale create -g demo --resource MyVmss \
--resource-type Microsoft.Compute/virtualMachineScaleSets \
--name autoscale \
--min-count 1 \
--max-count 2 \
--count 1"

  }
 depends_on = [
    null_resource.create_vmss
  ]
}

resource "null_resource" "rule_increase" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create \
--resource-group demo \  
--autoscale-name autoscale \  
--condition "Percentage CPU > 70 avg 1m" \
--scale out 1"

  }
 depends_on = [
    null_resource.autoscale_create
  ]
}

resource "null_resource" "rule_decrease" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group demo \
--autoscale-name autoscale \ 
--condition "Percentage CPU < 25 avg 1m" \
--scale in 1"
  }
 depends_on = [
    null_resource.autoscale_create
  ]
}
