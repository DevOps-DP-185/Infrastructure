resource "azurerm_subnet" "frontend" {
  name                 = "frontend"
  resource_group_name  = var.group_name
  virtual_network_name = var.network
  address_prefix       = "172.16.1.0/24"
}

resource "azurerm_public_ip" "gateway" {
  name                = "ip_gateway"
  resource_group_name = var.group_name
  location            = var.group_location
  allocation_method   = "Dynamic"
}

locals {
  backend_address_pool_name      = "azurerm_virtual_network.example.name-beap"
  frontend_port_name             = "azurerm_virtual_network.example.name-feport"
  frontend_ip_configuration_name = "azurerm_virtual_network.example.name-feip"
  http_setting_name              = "azurerm_virtual_network.example.name-be-htst"
  listener_name                  = "azurerm_virtual_network.example.name-httplstn"
  request_routing_rule_name      = "azurerm_virtual_network.example.name-rqrt"
  redirect_configuration_name    = "azurerm_virtual_network.example.name-rdrcfg"
}

resource "azurerm_application_gateway" "network" {
  name                = "example-appgateway"
  resource_group_name = var.group_name
  location            = var.group_location

  sku {
    name     = "Standard_Small"
    tier     = "Standard"
    capacity = 2
  }

  gateway_ip_configuration {
    name      = "my-gateway-ip-configuration"
    subnet_id = azurerm_subnet.frontend.id
  }

  frontend_port {
    name = "local.frontend_port_name-80"
    port = 80
  }

#  frontend_port {
#    name = "local.frontend_port_name-443"
#    port = 443
#  }

  frontend_port {
    name = "local.frontend_port_name-8761"
    port = 8761
  }

  frontend_ip_configuration {
    name                 = local.frontend_ip_configuration_name
    public_ip_address_id = azurerm_public_ip.gateway.id
  }

  backend_address_pool {
    name         = local.backend_address_pool_name
  }

  backend_http_settings {
    name                  = "local.http_setting_name-80"
    cookie_based_affinity = "Disabled"
    port                  = 80
    protocol              = "Http"
    request_timeout       = 30
  }

 # backend_http_settings {
 #   name                  = "local.http_setting_name-443"
 #   cookie_based_affinity = "Disabled"
 #   port                  = 443
 #   protocol              = "Https"
 #   request_timeout       = 30
 # }

  backend_http_settings {
    name                  = "local.http_setting_name-8761"
    cookie_based_affinity = "Disabled"
    port                  = 8761
    protocol              = "Http"
    request_timeout       = 30
  }

  http_listener {
    name                           = local.listener_name
    frontend_ip_configuration_name = local.frontend_ip_configuration_name
    frontend_port_name             = "local.frontend_port_name-80"
    protocol                       = "Http"
  }

  http_listener {
    name                           = "euruka"
    frontend_ip_configuration_name = local.frontend_ip_configuration_name
    frontend_port_name             = "local.frontend_port_name-8761"
    protocol                       = "Http"
  }

 # http_listener {
 #   name                           = "https-svagworks.me"
 #   frontend_ip_configuration_name = local.frontend_ip_configuration_name
 #   frontend_port_name             = "local.frontend_port_name-443"
 #   protocol                       = "Https"
 #   ssl_certificate_name           = "svagworks.me"
 # }

  request_routing_rule {
    name                       = "local.request_routing_rule_name-80"
    rule_type                  = "Basic"
    http_listener_name         = local.listener_name
    backend_address_pool_name  = local.backend_address_pool_name
    backend_http_settings_name = "local.http_setting_name-80"

}
#  request_routing_rule {
#    name                       = "local.request_routing_rule_name-443"
#    rule_type                  = "Basic"
#    http_listener_name         = "https-svagworks.me"
#    backend_address_pool_name  = local.backend_address_pool_name
#    backend_http_settings_name = "local.http_setting_name-443"

#}
  request_routing_rule {
    name                       = "local.request_routing_rule_name-8761"
    rule_type                  = "Basic"
    http_listener_name         = "euruka"
    backend_address_pool_name  = local.backend_address_pool_name
    backend_http_settings_name = "local.http_setting_name-8761"

 }
}
