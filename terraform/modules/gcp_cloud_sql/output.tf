output "private_ip_range_sql" {
    value = google_compute_global_address.private_ip_address.address
}