resource "null_resource" "create_vmss" {
  provisioner "local-exec" {
  command = "az vmss create  -n '${var.name_vm}' -g '${var.group_name}' --image  '${var.image_id}' --instance-count 1 --vm-sku Standard_D2s_v3 --vnet-name '${var.network}' --subnet '${var.subnet}' --app-gateway '${var.application_gateway}' --admin-username '${var.user}'"
    }
  }

resource "null_resource" "autoscale_create1" {
  provisioner "local-exec" {
  command = "az monitor autoscale create -g '${var.group_name}' --resource '${var.name_vm}' --resource-type Microsoft.Compute/virtualMachineScaleSets --name '${var.autoscale-name}' --min-count 1 --max-count 2 --count 1"

  }
 depends_on = [
    null_resource.create_vmss
  ]
}

resource "null_resource" "rule_increase1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group '${var.group_name}'  --autoscale-name '${var.autoscale-name}'  --condition 'Percentage CPU > 70 avg 1m'  --scale out 1"

  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}

resource "null_resource" "rule_decrease1" {
  provisioner "local-exec" {
  command = "az monitor autoscale rule create --resource-group '${var.group_name}' --autoscale-name '${var.autoscale-name}' --condition 'Percentage CPU < 25 avg 1m' --scale in 1"
  }
 depends_on = [
    null_resource.autoscale_create1
  ]
}
