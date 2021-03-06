output "vpn_static_ip" {
  value = google_compute_address.vpn_static_ip.address
}

output "shared_key" {
  value = var.shared_key
}

output "wait_tunnel" {
  value = google_compute_vpn_tunnel.tunnel.id
}
