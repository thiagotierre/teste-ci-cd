resource "kubernetes_namespace" "challengeone" {
  metadata {
    name = "challengeone"
    labels = {
      environment = "production"
      managed-by  = "terraform"
    }
  }
}