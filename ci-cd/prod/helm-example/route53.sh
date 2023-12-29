#!/bin/bash

# Creates route 53 records based on env name

aws route53 change-resource-record-sets --hosted-zone-id Z0686830SEKWQIDYCQTK --change-batch file://<(cat << EOF
{
  "Comment": "Testing creating a record set",
  "Changes": [
    {
      "Action": "CREATE",
      "ResourceRecordSet": {
        "Name": "uat.company.com",
        "Type": "CNAME",
        "TTL": 120,
        "ResourceRecords": [
          {
            "Value": "a24e468c7ebe04607af591202ca4978e-1113042226.us-east-1.elb.amazonaws.com"
          }
        ]
      }
    }
  ]
}
EOF
)
