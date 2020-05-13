variable "apis" {
    description = "Name of API that must be turn on"
    type = list(string)
    default = [ "sqladmin.googleapis.com", "servicenetworking.googleapis.com", "cloudresourcemanager.googleapis.com"]
}

variable "database_name" {
    description = "Database name for application"
    type = list(string)
    default = [ "identity", "vehicle", "messaging", "payment", "trip", "simulator"]    
}

variable "region" {
  description = "Region for CloudSQL"
  default = "us-east1"
}

variable "network_selflink" {
  description = "Self link of network for CloudSQL"
  default = "https://www.googleapis.com/compute/v1/projects/project-1-273710/global/networks/default"
}

variable "ip_range_for_cloudsql" {
  description = "Range for CloudSQL in VPC"
  default = "10.20.0.0"
}
