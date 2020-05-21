output "private_ip_address" {
    value = azurerm_linux_virtual_machine.example.private_ip_address
}
output "image_id" {
    value = azurerm_image.example.id
    }

