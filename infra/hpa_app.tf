resource "kubernetes_horizontal_pod_autoscaler_v2" "challengeone_hpa" {
  metadata {
    name      = "challengeone-hpa"
    namespace = "challengeone"
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "challengeone"
    }

    min_replicas = 2
    max_replicas = 5

    metric {
      type = "Resource"

      resource {
        name = "cpu"

        target {
          type               = "Utilization"
          average_utilization = 30
        }
      }
    }
  }
}
