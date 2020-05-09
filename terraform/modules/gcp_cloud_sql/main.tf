resource "google_project_service" "project" {
  for_each = toset(var.apis)
  service = each.value

  disable_dependent_services = true
}

resource "google_compute_global_address" "private_ip_address" {
  provider = google-beta

  name          = "private-ip-address"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  address       = var.ip_range_for_cloudsql
  prefix_length = 24
  network       = var.network_selflink
}

resource "google_service_networking_connection" "private_vpc_connection" {
  provider = google-beta

  network                 = var.network_selflink
  service                 = var.apis[1]
  reserved_peering_ranges = [google_compute_global_address.private_ip_address.name]
}

resource "random_id" "db_name_suffix" {
  byte_length = 4
}

resource "google_sql_database_instance" "master" {
  provider = google-beta

  name   = "private-instance-${random_id.db_name_suffix.hex}"
  region = var.region
  database_version = "POSTGRES_11"

  depends_on = [google_service_networking_connection.private_vpc_connection]

  settings {
    tier = "db-f1-micro"
    ip_configuration {
      ipv4_enabled    = false
      private_network = var.network_selflink
    }
  }
}

resource "google_sql_user" "users" {
  name     = "postgres"
  instance = google_sql_database_instance.master.name
  password = "postgres"
}

resource "google_sql_database" "database" {
  for_each = toset(var.database_name)
  name     = each.value
  instance = google_sql_database_instance.master.name
}