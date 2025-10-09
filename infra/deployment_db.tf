resource "kubernetes_stateful_set" "challengeone_db" {
  metadata {
    name      = "challengeone-db"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  spec {
    service_name = "challengeone-db"
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
            name       = "pgdata"
            mount_path = "/var/lib/postgresql/data"
          }
        }
      }
    }
    volume_claim_template {
      metadata {
        name = "pgdata"
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
  }
}
