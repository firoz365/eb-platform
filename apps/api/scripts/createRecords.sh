#!/bin/bash

BASE="http://localhost:8080/api/v1/services"

echo "Creating services..."

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Payments",
  "description": "Payment processing service",
  "owner": "team-payments",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Order",
  "description": "Order processing service",
  "owner": "ebrd",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Shipping",
  "description": "Shipping service",
  "owner": "ebrd",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Finance",
  "description": "Finance service",
  "owner": "ebrd",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Marketing",
  "description": "Marketing service",
  "owner": "ebrd",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Analytics",
  "description": "Analytics service",
  "owner": "team-data",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Reporting",
  "description": "Reporting service",
  "owner": "team-data",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Customer",
  "description": "Customer management service",
  "owner": "team-crm",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Notification",
  "description": "Notification service",
  "owner": "team-platform",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Inventory",
  "description": "Inventory management service",
  "owner": "team-ops",
  "status": "ACTIVE"
}'

curl -s -X POST "$BASE" -H "Content-Type: application/json" -d '{
  "name": "Support",
  "description": "Support service",
  "owner": "team-support",
  "status": "ACTIVE"
}'

echo "Done."