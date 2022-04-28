variable "region" {
  default = "us-east-1"
}

variable "access_key" {
  default = ""
}

variable "secret_key" {
  default = ""
}

variable "environment_name" {
  default = "dev"
}

variable "authorization_service_name" {
  default = "authorization-service"
}

variable "platform_service_name" {
  default = "REPLACE_ARTIFACT_NAME-multi-tenancy-tenant-platform-service"
}

variable "tenant_service_name" {
  default = "REPLACE_ARTIFACT_NAME-multi-tenancy-user-management-service"
}

variable "tenant_provisioning_service_name" {
  default = "REPLACE_ARTIFACT_NAME-multi-tenancy-tenant-provisioning-service"
}
