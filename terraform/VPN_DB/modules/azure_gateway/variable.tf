variable "private_ip_range" {}

variable "local_network_ip_range" {
    description = "Ip range for local network"
    default = ["10.128.0.0/16", "10.20.0.0/24"]
}

variable "shared_key" {}

variable "group_location" {}

variable "group_name" {}

variable "subnet_gateway_id" {}

variable "vpn_static_ip" {}

