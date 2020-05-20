
// create resource group

resource "azurerm_resource_group" "group" {
  name     = "demo4"
  location = var.region
}

// create virtual network and subnetwork

resource "azurerm_virtual_network" "network" {
  name                = "AGVNET"
  resource_group_name = azurerm_resource_group.group.name
  location            = var.region
  address_space       = var.network_space_ip
}

resource "azurerm_subnet" "subnet" {
  name                 = "Sub"
  virtual_network_name = azurerm_virtual_network.network.name
  resource_group_name  = azurerm_resource_group.group.name
  address_prefix       = var.subnet_range_ip
}

resource "azurerm_subnet" "subnet_gateway" {
  name                 = "GatewaySubnet"
  virtual_network_name = azurerm_virtual_network.network.name
  resource_group_name  = azurerm_resource_group.group.name
  address_prefix       = var.gateway_subnet_range_ip
}
