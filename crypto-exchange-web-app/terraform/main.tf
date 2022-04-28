provider "aws" {
  region = var.region
  access_key = var.access_key
  secret_key = var.secret_key
}

// --------------- Start Caller details ---------------
data "aws_caller_identity" "current" {
}
// --------------- End Caller details ---------------

// --------------- Start FIFO Queues ---------------
// Queue to handle the new tenant requests i.e. whenever a new tenant is onboarded, a message is published to
// this queue.
resource "aws_sqs_queue" "fifo-queue-data-vision-new-tenant-requests" {
  name = "${var.environment_name}-data-vision-new-tenant-requests.fifo"
  fifo_queue = true
}

// Queue to handle the deletion of tenants i.e. whenever an existing tenant is marked for deletion, a message is
// published to this queue.
resource "aws_sqs_queue" "fifo-queue-data-vision-delete-tenant-requests" {
  name = "${var.environment_name}-data-vision-delete-tenant-requests.fifo"
  fifo_queue = true
}

// Queue to handle the restoration of tenants i.e. requests to restore a tenant, which was previously marked for
// deletion are published to this queue.
resource "aws_sqs_queue" "fifo-queue-data-vision-restore-tenant-requests" {
  name = "${var.environment_name}-data-vision-restore-tenant-requests.fifo"
  fifo_queue = true
}
// --------------- End FIFO Queues ---------------

// --------------- Start RDS Events ---------------
// Topic ([env-name]-data-vision-rds-events) to handle the RDS events and will fan out to respective queues.
resource "aws_sns_topic" "topic-data-vision-rds-events" {
  name = "${var.environment_name}-data-vision-rds-events"
}

// Topic subscription to listen to events from RDS.
resource "aws_db_event_subscription" "subscription-data-vision-rds-events" {
  name = "${var.environment_name}-data-vision-rds-events-subscription"
  sns_topic = aws_sns_topic.topic-data-vision-rds-events.arn
  source_type = "db-instance"
}

// Queue to handle the db lifecycle events i.e. we listen to the RDS events, which come to the topic, which further
// fans out to this queue.
resource "aws_sqs_queue" "queue-data-vision-db-lifecycle-events" {
  name = "${var.environment_name}-data-vision-db-lifecycle-events"
}

// Queue policy for [env]-data-vision-db-lifecycle-events.
resource "aws_sqs_queue_policy" "queue-policy-data-vision-db-lifecycle-events" {
  queue_url = aws_sqs_queue.queue-data-vision-db-lifecycle-events.id
  policy = <<EOF
  {
    "Version": "2008-10-17",
    "Id": "__default_policy_ID",
    "Statement": [
      {
        "Sid": "__owner_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": "SQS:*",
        "Resource": "${aws_sqs_queue.queue-data-vision-db-lifecycle-events.arn}"
      },
      {
        "Sid": "__sender_statement",
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue-data-vision-db-lifecycle-events.arn}"
      },
      {
        "Sid": "__receiver_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": [
          "SQS:ChangeMessageVisibility",
          "SQS:DeleteMessage",
          "SQS:ReceiveMessage"
        ],
        "Resource": "${aws_sqs_queue.queue-data-vision-db-lifecycle-events.arn}"
      }
    ]
  }
  EOF
}

// Topic subscription for queue-data-vision-db-lifecycle-events
resource "aws_sns_topic_subscription" "topic-subscription-queue-data-vision-db-lifecycle-events" {
  endpoint = aws_sqs_queue.queue-data-vision-db-lifecycle-events.arn
  protocol = "sqs"
  topic_arn = aws_sns_topic.topic-data-vision-rds-events.arn
}
// --------------- End RDS Events ---------------

// --------------- Start Refresh Data-sources ---------------
// Topic ([env-name]-data-vision-refresh-datasources) to handle refreshing of data sources in tenant-aware
// microservices (e.g. platform-service, tenant-service, tenant-provisioning-service, authorization-service, etc.).
resource "aws_sns_topic" "topic-data-vision-refresh-datasources" {
  name = "${var.environment_name}-data-vision-refresh-datasources"
}

// Queue for [env]-authorization-service-data-vision-refresh-datasources.
resource "aws_sqs_queue" "queue-authorization-service-data-vision-refresh-datasources" {
  name = "${var.environment_name}-refresh-${var.authorization_service_name}"
}

// Queue policy for [env]-authorization-service-data-vision-refresh-datasources.
resource "aws_sqs_queue_policy" "queue-policy-authorization-service-data-vision-refresh-datasources" {
  queue_url = aws_sqs_queue.queue-authorization-service-data-vision-refresh-datasources.id
  policy = <<EOF
  {
    "Version": "2008-10-17",
    "Id": "__default_policy_ID",
    "Statement": [
      {
        "Sid": "__owner_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": "SQS:*",
        "Resource": "${aws_sqs_queue.queue-authorization-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__sender_statement",
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue-authorization-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__receiver_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": [
          "SQS:ChangeMessageVisibility",
          "SQS:DeleteMessage",
          "SQS:ReceiveMessage"
        ],
        "Resource": "${aws_sqs_queue.queue-authorization-service-data-vision-refresh-datasources.arn}"
      }
    ]
  }
  EOF
}

// Topic subscription for queue-authorization-service-data-vision-refresh-datasources
resource "aws_sns_topic_subscription" "topic-subscription-queue-authorization-service-data-vision-refresh-datasources" {
  endpoint = aws_sqs_queue.queue-authorization-service-data-vision-refresh-datasources.arn
  protocol = "sqs"
  topic_arn = aws_sns_topic.topic-data-vision-refresh-datasources.arn
}

// Queue for [env]-platform-service-data-vision-refresh-datasources
resource "aws_sqs_queue" "queue-platform-service-data-vision-refresh-datasources" {
  name = "${var.environment_name}-refresh-${var.platform_service_name}"
}

// Queue policy for [env]-platform-service-data-vision-refresh-datasources
resource "aws_sqs_queue_policy" "queue-platform-service-data-vision-refresh-datasources" {
  queue_url = aws_sqs_queue.queue-platform-service-data-vision-refresh-datasources.id
  policy = <<EOF
  {
    "Version": "2008-10-17",
    "Id": "__default_policy_ID",
    "Statement": [
      {
        "Sid": "__owner_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": "SQS:*",
        "Resource": "${aws_sqs_queue.queue-platform-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__sender_statement",
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue-platform-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__receiver_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": [
          "SQS:ChangeMessageVisibility",
          "SQS:DeleteMessage",
          "SQS:ReceiveMessage"
        ],
        "Resource": "${aws_sqs_queue.queue-platform-service-data-vision-refresh-datasources.arn}"
      }
    ]
  }
  EOF
}

// Topic subscription for queue-platform-service-data-vision-refresh-datasources
resource "aws_sns_topic_subscription" "topic-subscription-queue-platform-service-data-vision-refresh-datasources" {
  endpoint = aws_sqs_queue.queue-platform-service-data-vision-refresh-datasources.arn
  protocol = "sqs"
  topic_arn = aws_sns_topic.topic-data-vision-refresh-datasources.arn
}

// Queue for [env]-tenant-service-data-vision-refresh-datasources
resource "aws_sqs_queue" "queue-tenant-service-data-vision-refresh-datasources" {
  name = "${var.environment_name}-refresh-${var.tenant_service_name}"
}

// Queue policy for [env]-tenant-service-data-vision-refresh-datasources
resource "aws_sqs_queue_policy" "queue-tenant-service-data-vision-refresh-datasources" {
  queue_url = aws_sqs_queue.queue-tenant-service-data-vision-refresh-datasources.id
  policy = <<EOF
  {
    "Version": "2008-10-17",
    "Id": "__default_policy_ID",
    "Statement": [
      {
        "Sid": "__owner_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": "SQS:*",
        "Resource": "${aws_sqs_queue.queue-tenant-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__sender_statement",
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue-tenant-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__receiver_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": [
          "SQS:ChangeMessageVisibility",
          "SQS:DeleteMessage",
          "SQS:ReceiveMessage"
        ],
        "Resource": "${aws_sqs_queue.queue-tenant-service-data-vision-refresh-datasources.arn}"
      }
    ]
  }
  EOF
}

// Topic subscription for queue-tenant-service-data-vision-refresh-datasources
resource "aws_sns_topic_subscription" "topic-subscription-queue-tenant-service-data-vision-refresh-datasources" {
  endpoint = aws_sqs_queue.queue-tenant-service-data-vision-refresh-datasources.arn
  protocol = "sqs"
  topic_arn = aws_sns_topic.topic-data-vision-refresh-datasources.arn
}

// Queue for [env]-tenant-provisioning-service-data-vision-refresh-datasources
resource "aws_sqs_queue" "queue-tenant-provisioning-service-data-vision-refresh-datasources" {
  name = "${var.environment_name}-refresh-${var.tenant_provisioning_service_name}"
}

// Queue policy for [env]-tenant-provisioning-service-data-vision-refresh-datasources
resource "aws_sqs_queue_policy" "queue-tenant-provisioning-service-data-vision-refresh-datasources" {
  queue_url = aws_sqs_queue.queue-tenant-provisioning-service-data-vision-refresh-datasources.id
  policy = <<EOF
  {
    "Version": "2008-10-17",
    "Id": "__default_policy_ID",
    "Statement": [
      {
        "Sid": "__owner_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": "SQS:*",
        "Resource": "${aws_sqs_queue.queue-tenant-provisioning-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__sender_statement",
        "Effect": "Allow",
        "Principal": {
          "Service": "sns.amazonaws.com"
        },
        "Action": "SQS:SendMessage",
        "Resource": "${aws_sqs_queue.queue-tenant-provisioning-service-data-vision-refresh-datasources.arn}"
      },
      {
        "Sid": "__receiver_statement",
        "Effect": "Allow",
        "Principal": {
          "AWS": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        },
        "Action": [
          "SQS:ChangeMessageVisibility",
          "SQS:DeleteMessage",
          "SQS:ReceiveMessage"
        ],
        "Resource": "${aws_sqs_queue.queue-tenant-provisioning-service-data-vision-refresh-datasources.arn}"
      }
    ]
  }
  EOF
}

// Topic subscription for queue-tenant-provisioning-service-data-vision-refresh-datasources
resource "aws_sns_topic_subscription" "topic-subscription-queue-tenant-provisioning-service-data-vision-refresh-datasources" {
  endpoint = aws_sqs_queue.queue-tenant-provisioning-service-data-vision-refresh-datasources.arn
  protocol = "sqs"
  topic_arn = aws_sns_topic.topic-data-vision-refresh-datasources.arn
}
// --------------- End Refresh Data-sources ---------------
