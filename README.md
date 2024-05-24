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
      "url": "<rabbitmq host>"
      "username": "<username>",
      "password": "<password>",
      "virtual_host": "<vhost>",
      "durable": false,
      "channel_expiry": 60000
    }
  }
}
```