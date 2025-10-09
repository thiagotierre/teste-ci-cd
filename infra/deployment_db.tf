resource "kubernetes_deployment" "challengeone_db" {
  metadata {
    name      = "challengeone-db"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "challengeone-db"
      }
    }

    template {
      metadata {
        labels = {
          app = "challengeone-db"
        }
      }

      spec {
        container {
          name  = "postgres"
          image = "postgres:16"

          port {
            container_port = 5432
          }

          env_from {
            secret_ref {
              name = kubernetes_secret.challengeone_db.metadata[0].name
            }
          }

          volume_mount {
            name       = "pgdata-pvc"
            mount_path = "/var/lib/postgresql/data"
          }
        }

        volume {
          name = "pgdata-pvc"
          persistent_volume_claim {
            claim_name = kubernetes_persistent_volume_claim.pgdata.metadata[0].name
          }
        }
      }
    }
  }
}
