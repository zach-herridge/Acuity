package com.acuitybotting.data.flow.messaging.services.client;

import com.acuitybotting.data.flow.messaging.services.Message;
import com.acuitybotting.data.flow.messaging.services.client.exceptions.MessagingException;
import com.acuitybotting.data.flow.messaging.services.events.MessageEvent;
import com.acuitybotting.data.flow.messaging.services.futures.MessageFuture;

import java.util.Objects;
import java.util.concurrent.Future;

import static com.acuitybotting.data.flow.messaging.services.client.MessagingClient.RESPONSE_ID;
import static com.acuitybotting.data.flow.messaging.services.client.MessagingClient.RESPONSE_QUEUE;

/**
 * Created by Zachary Herridge on 7/2/2018.
 */
public interface MessagingChannel {

    MessagingQueue createQueue(String queue, boolean create);

    MessagingChannel close() throws MessagingException;

    MessagingClient getClient();

    void acknowledge(Message message) throws MessagingException;

    default void sendToQueue(String queue, String body) throws MessagingException {
        send("", queue, body);
    }

    default Future<MessageEvent> sendToQueue(String queue, String localQueue, String body) throws MessagingException {
        return send("", queue, localQueue, body);
    }

    default void send(String exchange, String routingKey, String body) throws MessagingException {
        send(exchange, routingKey, null, null, body);
    }

    default Future<MessageEvent> send(String exchange, String routingKey, String localQueue, String body) throws MessagingException {
        return send(exchange, routingKey, localQueue, null, body);
    }

    default void respond(Message message, String body) throws MessagingException {
        respond(message, null, body);
    }

    default Future<MessageEvent> respond(Message message, String localQueue, String body) throws MessagingException {
        String responseTopic = message.getAttributes().get(RESPONSE_QUEUE);
        String responseId = message.getAttributes().get(RESPONSE_ID);

        Objects.requireNonNull(responseTopic);
        Objects.requireNonNull(responseId);

        return send("", responseTopic, localQueue, responseId, body);
    }

    Future<MessageEvent> send(String targetExchange, String targetRouting, String localQueue, String futureId, String body) throws MessagingException;

    MessageFuture getMessageFuture(String id);
}
