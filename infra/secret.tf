resource "kubernetes_secret" "challengeone_db" {
  metadata {
    name      = "challengeone-db-secret"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  data = {
    POSTGRES_USER     = "postgres"
    POSTGRES_PASSWORD = "123"
    POSTGRES_DB       = "challengeone"
    SPRING_DATASOURCE_URL = "jdbc:postgresql://challengeone-db:5432/challengeone"
    SPRING_DATASOURCE_USERNAME = "postgres"
    SPRING_DATASOURCE_PASSWORD = "123"
  }

  type = "Opaque"
}
