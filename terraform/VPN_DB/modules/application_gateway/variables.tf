variable "group_location" {}
variable "group_name" {}
variable "network" {}
variable "ssl_policy_name" {
  description = "SSL policy name"
  default     = "AppGwSslPolicy20170401S"
}
variable "certificate_content" {}
variable "certificate_passphrase" {}
