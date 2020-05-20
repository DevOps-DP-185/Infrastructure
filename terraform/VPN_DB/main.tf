provider "azurerm" {
  version         = "=2.0.0"
  features {}
}

provider "google" {
  region    = "us-east1"
  version   = "=3.0.0"
  project   = "project-1-273710"
  credentials = "/home/project-1-273710-867758a0793e.json"
  }

provider "google-beta" {
  region = "us-east1"
  zone   = "us-east1-a"
  version = "=3.20"
  project   = "project-1-273710"
  credentials = "/home/project-1-273710-867758a0793e.json"
}

module "azure_vpc" {
  source = "./modules/azure_vpc"
}

module "azure_gateway" {
    source = "./modules/azure_gateway"

    group_location = module.azure_vpc.group_location
    group_name = module.azure_vpc.group_name
    subnet_gateway_id = module.azure_vpc.subnet_gateway_id
    vpn_static_ip = module.gcp_gateway.vpn_static_ip
    shared_key = module.gcp_gateway.shared_key
    private_ip_range = module.gcp_cloud_sql.private_ip_range_sql
}

module "gcp_gateway" {
    source = "./modules/gcp_gateway"

    azure_public_ip = module.azure_gateway.public_ip
}

module "gcp_cloud_sql" {
  source = "./modules/gcp_cloud_sql"
  
  database_username = var.database_username
  database_password = var.database_password
}
  
 module "azure_vm" {
  source = "./modules/azure_vm"

  group_location = module.azure_vpc.group_location
  group_name     = module.azure_vpc.group_name
  subnet_id      = module.azure_vpc.subnet_id
}

module "application_gateway" {
  source = "./modules/application_gateway"

  group_location     = module.azure_vpc.group_location
  group_name         = module.azure_vpc.group_name
  network            = module.azure_vpc.network
  private_ip_address = module.azure_vm.private_ip_address

}
 
module "vm_scale_set" {
  source = "./modules/vm_scale_set"

  group_location      = module.azure_vpc.group_location
  group_name          = module.azure_vpc.group_name
  network             = module.azure_vpc.network
  application_gateway = module.application_gateway.applicatiom_gateway

}  
  
resource "null_resource" "pingdom" {
  provisioner "local-exec" {
    command = "python pingdom.py"
  }
}
