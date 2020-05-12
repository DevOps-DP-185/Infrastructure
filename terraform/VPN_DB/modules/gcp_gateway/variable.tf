variable "shared_key" {
  description = "Shared key for the vpn gateways"
  default = "12345678"
}

variable "range_traffic" {
  description = "Range ip for vpn tunnel"
  default = ["10.20.0.0/24", "10.128.0.0/20"]
}

variable "network_selflink" {
  description = "Self link of network for CloudSQL"
  default = "https://www.googleapis.com/compute/v1/projects/winged-polygon-271409/global/networks/default"
}

variable "dest_range" {
  description = "Destination range for route of vpn tunnel"
  default = "172.16.0.0/16"
}


variable "azure_public_ip" {}
