resource "azurerm_public_ip" "gateway_ip" {
  name                = "AG-Virtualnet-GW"
  location            = var.group_location
  resource_group_name = var.group_name

  allocation_method = "Dynamic"
}

resource "azurerm_virtual_network_gateway" "gateway" {
  name                = "AG-Virtualnet-GW"
  location            = var.group_location
  resource_group_name = var.group_name

  type     = "Vpn"
  vpn_type = "RouteBased"

  active_active = false
  enable_bgp    = false
  sku           = "Basic"
  
  ip_configuration {
    name                          = "vnetGatewayConfig"
    public_ip_address_id          = azurerm_public_ip.gateway_ip.id
    private_ip_address_allocation = "Dynamic"
    subnet_id                     = var.subnet_gateway_id
  }
  
  lifecycle {
    ignore_updates = true
}
}

resource "azurerm_local_network_gateway" "local_gateway" {
  name                = "LocalnetGW"
  resource_group_name = var.group_name
  location            = var.group_location
  gateway_address     = var.vpn_static_ip
  address_space       = var.local_network_ip_range
}

resource "azurerm_virtual_network_gateway_connection" "connection" {
  name                = "azure-google"
  location            = var.group_location
  resource_group_name = var.group_name
  connection_protocol = "IKEv2"

  type                       = "IPsec"
  virtual_network_gateway_id = azurerm_virtual_network_gateway.gateway.id
  local_network_gateway_id   = azurerm_local_network_gateway.local_gateway.id

  shared_key = var.shared_key
}
