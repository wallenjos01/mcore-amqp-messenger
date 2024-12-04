# RabbitMQMessenger

An addon for the MidnightCore messenger module which allows sending messages via RabbitMQ.

### Sample Configuration

`MidnightCore/modules.json`:
```json
"midnightcore:messenger": {
  "enabled": true,
  "messengers": {
    "default": {
      "type": "amqp",
      "hostname": "<rabbitmq host>",
      "port": 5672
      "username": "<username>",
      "password": "<password>",
      "virtual_host": "<vhost>",
      "exchange": "<exchange>",
      "exchange_type": "direct"
      "durable": false
    }
  }
}
```