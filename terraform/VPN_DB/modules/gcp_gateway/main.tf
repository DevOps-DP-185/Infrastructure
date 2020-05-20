
resource "google_compute_vpn_gateway" "gateway" {
  name    = "google-azure"
  network = "default"
}

resource "google_compute_address" "vpn_ip_static" {
  name = "vpn-static-ip"
}

resource "google_compute_forwarding_rule" "fr_esp" {
  name        = "fr-esp"
  ip_protocol = "ESP"
  ip_address  = google_compute_address.vpn_static_ip.address
  target      = google_compute_vpn_gateway.gateway.self_link
}

resource "google_compute_forwarding_rule" "fr_udp500" {
  name        = "fr-udp500"
  ip_protocol = "UDP"
  port_range  = "500"
  ip_address  = google_compute_address.vpn_static_ip.address
  target      = google_compute_vpn_gateway.gateway.self_link
}

resource "google_compute_forwarding_rule" "fr_udp4500" {
  name        = "fr-udp4500"
  ip_protocol = "UDP"
  port_range  = "4500"
  ip_address  = google_compute_address.vpn_static_ip.address
  target      = google_compute_vpn_gateway.gateway.self_link
}

resource "google_compute_vpn_tunnel" "tunnel" {
  name          = "tunnel-google-azure"
  peer_ip       = var.azure_public_ip
  shared_secret = var.shared_key
  ike_version   = 2
  local_traffic_selector = var.range_traffic

  target_vpn_gateway = google_compute_vpn_gateway.gateway.self_link

  depends_on = [
    google_compute_forwarding_rule.fr_esp,
    google_compute_forwarding_rule.fr_udp500,
    google_compute_forwarding_rule.fr_udp4500,
  ]
}

resource "google_compute_route" "route" {
  name       = "route"
  network    = var.network_selflink
  dest_range = var.dest_range
  priority   = 1000

  next_hop_vpn_tunnel = google_compute_vpn_tunnel.tunnel.self_link
}
