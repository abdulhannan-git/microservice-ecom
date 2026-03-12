RabbitMQ:

RabbitMQ: Docker installation
-----------------------------

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management

RabbitMQ Docker compose configuration:
--------------------------------------

  rabbitmq:
    container_name: rabbitmq_container
    image: rabbitmq:4-management
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    ports:
      - "5672:5672" #RabbitMQ Message Broker
      - "15672:15672" #RabbitMQ Management UI (http://localhost:15672)
    networks:
      - backed
    restart: unless-stopped
---------------------------------
RabbitMQ URL: http://localhost:15672


networks:
  backend:
    driver: bridge
