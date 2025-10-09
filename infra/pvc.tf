resource "kubernetes_persistent_volume_claim" "pgdata" {
  metadata {
    name      = "pgdata-pvc"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  spec {
    access_modes = ["ReadWriteOnce"]

    resources {
      requests = {
        storage = "1Gi"
      }
    }
    storage_class_name = "local-path"
  }
}
