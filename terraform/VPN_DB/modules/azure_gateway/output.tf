output "public_ip" {
    value = azurerm_public_ip.gateway_ip.ip_address
}

output "connection" {
    value = azurerm_virtual_network_gateway_connection.connection.id
}
