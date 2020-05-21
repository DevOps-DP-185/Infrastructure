resource "null_resource" "create_vmss" {
  provisioner "local-exec" {
  command = "az vmss create  -n '${name_vm}' -g '${var.group_name}' --image  '${image_id}' --instance-count 1 --vm-sku Standard_D2s_v3 --vnet-name '${network}' --subnet '${subnet}' --app-gateway '${application_gateway}' --admin-username '${user}'"
  }
 depends_on = [
    var.image_id
  ]
  }

resource "null_resource" "autoscale_create1" {
  provisioner "local-exec" {
  command = "az monitor autoscale create -g '${var.group_name}' --resource '${name_vm}' --resource-type Microsoft.Compute/virtualMachineScaleSets --name '${autoscale-name}' --min-count 1 --max-count 2 --count 1"

  }
 depends_on = [
    null_resource.create_vmss
  ]
}

resource "null_resource" "rule_increase1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group '${var.group_name}'  --autoscale-name '${autoscale-name}'  --condition 'Percentage CPU > 70 avg 1m'  --scale out 1"

  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}

resource "null_resource" "rule_decrease1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group '${var.group_name}' --autoscale-name '${autoscale-name}' --condition 'Percentage CPU < 25 avg 1m' --scale in 1"
  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}
