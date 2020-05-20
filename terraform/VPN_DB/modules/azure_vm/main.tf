#resource "azurerm_public_ip" "example" {
 # name                    = "test-pip"
 # location                = var.group_location
 # resource_group_name     = var.group_name
 # allocation_method       = "Static"
 # idle_timeout_in_minutes = 30
#}

resource "azurerm_network_interface" "example" {
  name                = "example-nic"
  location            = var.group_location
  resource_group_name = var.group_name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = var.subnet_id
    private_ip_address_allocation = "Dynamic"
   # public_ip_address_id          = azurerm_public_ip.example.id
  }
}

resource "azurerm_linux_virtual_machine" "example" {
  name                  = "example-machine"
  resource_group_name   = var.group_name
  location              = var.group_location
  size                  = "Standard_F2"
  admin_username        = "artemkulish123"
  network_interface_ids = [
    azurerm_network_interface.example.id,
  ]

  admin_ssh_key {
    username   = "artemkulish123"
    public_key = file("~/.ssh/id_rsa.pub")
  }

  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }

  source_image_reference {
    publisher = "Debian"
    offer     = "debian-10"
    sku       = "10"
    version   = "latest"
  }

    connection {
      type        = "ssh"
      user        = "artemkulish123"
      private_key = file("~/.ssh/id_rsa")
      host     = azurerm_linux_virtual_machine.example.private_ip_address   
      #   host        = azurerm_public_ip.example.ip_address
      }

 provisioner "remote-exec" {
      inline = [
          "sudo apt-get update && sudo apt upgrade -y",
          "sudo apt install apt-transport-https ca-certificates curl gnupg2 software-properties-common -y",
          "curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -",
          "sudo add-apt-repository 'deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable'",
          "sudo apt update",
          "sudo apt install docker-ce -y",
          "sudo apt install docker-compose -y"
         ]
      }
  provisioner "file" {
  source      = "../Deploy/deploy.sh"
  destination = "/home/artemkulish123/deploy.sh"
   }

 provisioner "file" {
  source      = "/home/env"
  destination = "/home/artemkulish123/env"
  }
 provisioner "file" {
  source      = "../Deploy/docker-compose.yml"
  destination = "/home/artemkulish123/docker-compose.yml"
 }

 provisioner "remote-exec" {
      inline = [
         "sudo waagent -deprovision -force"
      ]
  }
   depends_on = [
    var.connection, var.connection2
  ]
}

resource "null_resource" "test81" {
  provisioner "local-exec" {
  command = "az vm deallocate --resource-group '${var.group_name}' --name '${azurerm_linux_virtual_machine.example.name}'"
  }
 depends_on = [
    azurerm_linux_virtual_machine.example
  ]
}

resource "null_resource" "test91" {
  provisioner "local-exec" {
  command = "az vm generalize --resource-group '${var.group_name}' --name '${azurerm_linux_virtual_machine.example.name}'"
  }
 depends_on = [
    null_resource.test81
  ]
}
resource "azurerm_image" "example" {
  name                      = "image"
  location                  = var.group_location
  resource_group_name       = var.group_name
  source_virtual_machine_id = azurerm_linux_virtual_machine.example.id

depends_on = [
  null_resource.test91
  ]
}

resource "null_resource" "test101" {
  provisioner "local-exec" {
  command = "az vm delete -g '${var.group_name}' -n '${azurerm_linux_virtual_machine.example.name}' --yes"
  }
 depends_on = [
    azurerm_image.example
  ]
}
