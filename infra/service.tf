# svc do banco
resource "kubernetes_service" "challengeone_db" {
  metadata {
    name      = "challengeone-db"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  spec {
    cluster_ip = "None"
    selector = {
      app = "challengeone-db"
    }

    port {
      port        = 5432
      target_port = 5432
    }
  }
}

# svc do app
resource "kubernetes_service" "challengeone_app" {
  metadata {
    name      = "challengeone-service"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
    labels = {
      app = "challengeone"
    }
  }

  spec {
    selector = {
      app = "challengeone"
    }

    port {
      protocol    = "TCP"
      port        = 8080
      target_port = 8080
      node_port   = 30080
    }

    type = "NodePort"
  }
}
