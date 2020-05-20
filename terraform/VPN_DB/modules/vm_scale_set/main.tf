resource "null_resource" "create_vmss101" {
  provisioner "local-exec" {
  command = "az vmss create  -n MyVmss -g group --image  /subscriptions/92c6b1c1-2921-4a64-b4dc-dd0f92882bf6/resourceGroups/group/providers/Microsoft.Compute/images/image --instance-count 1 --vm-sku Standard_D2s_v3 --vnet-name AGVNET --subnet Sub --app-gateway example-appgateway --admin-username artemkulish123"
  }
 depends_on = [
    var.image_id
  ]
  }

resource "null_resource" "autoscale_create1" {
  provisioner "local-exec" {
  command = "az monitor autoscale create -g group --resource MyVmss --resource-type Microsoft.Compute/virtualMachineScaleSets --name autoscale --min-count 1 --max-count 2 --count 1"

  }
 depends_on = [
    null_resource.create_vmss101
  ]
}

resource "null_resource" "rule_increase1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group group  --autoscale-name autoscale   --condition 'Percentage CPU > 70 avg 1m'  --scale out 1"

  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}

resource "null_resource" "rule_decrease1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group group --autoscale-name autoscale --condition 'Percentage CPU < 25 avg 1m' --scale in 1"
  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}
