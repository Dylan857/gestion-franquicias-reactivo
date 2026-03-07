terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "npipe:////./pipe/docker_engine"
}

resource "docker_image" "postgres" {
  name = "postgres:15-alpine"
}

resource "docker_container" "db" {
  name  = "postgres-db"
  image = docker_image.postgres.image_id

  env = [
    "POSTGRES_DB=prueba_tecnica_accenture",
    "POSTGRES_USER=admin",
    "POSTGRES_PASSWORD=Admin123@"
  ]

  ports {
    internal = 5432
    external = 5432
  }

  volumes {
    host_path      = "${abspath(path.module)}/../init.sql"
    container_path = "/docker-entrypoint-initdb.d/init.sql"
  }
}