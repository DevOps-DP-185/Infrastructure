variable "apis" {
    description = "Name of API that must be turn on"
    type = list(string)
    default = [ "sqladmin.googleapis.com", "servicenetworking.googleapis.com"]
}

variable "database_name" {
    description = "Database name for application"
    type = list(string)
    default = [ "identity", "vehicle", "messaging", "payment", "trip"]    
}

variable "region" {
  description = "Region for CloudSQL"
  default = "us-central1"
}

variable "network_selflink" {
  description = "Self link of network for CloudSQL"
  default = "https://www.googleapis.com/compute/v1/projects/winged-polygon-271409/global/networks/default"
}

variable "ip_range_for_cloudsql" {
  description = "Range for CloudSQL in VPC"
  default = "10.20.0.0"
}
