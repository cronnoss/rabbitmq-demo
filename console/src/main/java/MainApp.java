import com.rabbitmq.client.*;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

//        AMQP.Queue.DeclareOk response = channel.queueDeclarePassive("direct-queue");
//        response.getMessageCount();
//        response.getConsumerCount();

//        channel.exchangeDeclare("dexp", "direct", true);
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName, "dexp", "woo");
//
//        channel.basicPublish("dexp", "woo", null, "Java".getBytes());
//
//        channel.basicConsume(queueName, new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println(new String(body));
//            }
//        } );

        channel.exchangeDeclare("fexp", "fanout", true);
        String queueName1 = channel.queueDeclare().getQueue();
        String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName1, "fexp", "woo");
        channel.queueBind(queueName2, "fexp", "woo");

        channel.basicPublish("fexp", "woo", null, "Java".getBytes());

        channel.basicConsume(queueName1, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

        channel.basicConsume(queueName2, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(">" + new String(body));
            }
        });

//        channel.queueBind("direct-queue", "direct-exchange", "woo");

//        channel.basicPublish("direct-exchange", "woo", null, "Hello".getBytes());

//        channel.basicConsume("direct-queue", false, "myConsumerTag", new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String routingKey = envelope.getRoutingKey();
//                String contentType = properties.getContentType();
//                long deliveryTag = envelope.getDeliveryTag();
//                channel.basicAck(deliveryTag, false);
//                System.out.println(new String(body));
//            }
//        });
    }
}
