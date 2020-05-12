output "network" {
    value = azurerm_virtual_network.network.name
}

output "group_location" {
    value = azurerm_resource_group.group.location
}

output "group_name" {
    value = azurerm_resource_group.group.name
}

output "subnet_gateway_id" {
  value = azurerm_subnet.subnet_gateway.id
}
