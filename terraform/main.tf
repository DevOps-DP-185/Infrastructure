provider "azurerm" {
  version = "=2.0.0"
  features {
  }
}

provider "google" {
  region    = "us-central1"
  version   = "=3.0.0"
}

provider "google-beta" {
  region = "us-central1"
  zone   = "us-central1-a"
  version = "=3.20"
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
}