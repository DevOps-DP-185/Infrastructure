variable "region" {
  default = "Central US"
  description = "The region for all resource in Azure" 
}

variable "network_space_ip" {
  default = ["172.16.0.0/16"]
  description = "IP range for the network" 
}

variable "subnet_range_ip" {
  description = "IP range for the subnet" 
  default = "172.16.0.0/24"
}

variable "gateway_subnet_range_ip" {
  description = "IP range for the Gateway subnet"
  default = "172.16.10.0/24" 
}