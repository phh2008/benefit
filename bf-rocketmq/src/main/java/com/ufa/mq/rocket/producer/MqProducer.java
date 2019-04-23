package com.ufa.mq.rocket.producer;

import com.google.common.base.Strings;
import com.ufa.mq.rocket.exception.RocketMQException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

/**
 * 默认生产者
 * Created by phh on 2017/11/21.
 *
 * @author phh
 */
public class MqProducer extends DefaultMQProducer {
    /**
     * 日志
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MqProducer.class);

    /**
     * Spring bean init-method
     */
    public void init() {
        if (Strings.isNullOrEmpty(this.getProducerGroup())) {
            throw new RocketMQException("groupName is blank");
        }
        if (Strings.isNullOrEmpty(this.getNamesrvAddr())) {
            throw new RocketMQException("nameServerAddr is blank");
        }
        if (Strings.isNullOrEmpty(this.getInstanceName())) {
            throw new RocketMQException("instanceName is blank");
        }
        try {
            this.start();
            LOGGER.info(String.format("MqProducer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , this.getProducerGroup(), this.getNamesrvAddr()));
        } catch (MQClientException e) {
            LOGGER.error(String.format("MqProducer is error %s", e.getMessage()), e);
            throw new RocketMQException(e);
        }
    }


    /**
     * 异步发送
     *
     * @param topic
     * @param tags
     * @param body
     * @param consumer maybe null
     */
    public void sendAsync(String topic, String tags, byte[] body, BiConsumer<SendResult, Throwable> consumer) {
        Message msg = new Message();
        msg.setBody(body);
        msg.setTopic(topic);
        msg.setTags(tags);
        sendAsyncExtract(consumer, msg);
    }

    /**
     * 异步发送
     *
     * @param msg
     * @param consumer maybe null
     */
    public void sendAsync(Message msg, BiConsumer<SendResult, Throwable> consumer) {
        sendAsyncExtract(consumer, msg);
    }

    /**
     * 发送消息提取
     *
     * @param consumer
     * @param msg
     */
    private void sendAsyncExtract(BiConsumer<SendResult, Throwable> consumer, Message msg) {
        try {
            this.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    if (consumer != null) {
                        consumer.accept(sendResult, null);
                    } else {
                        LOGGER.info("onSuccess-{}", sendResult);
                    }
                }

                @Override
                public void onException(Throwable e) {
                    if (consumer != null) {
                        consumer.accept(null, e);
                    } else {
                        LOGGER.error("onException", e);
                    }
                }
            });
        } catch (Exception e) {
            if (consumer != null) {
                consumer.accept(null, e);
            } else {
                LOGGER.error("onException", e);
            }
        }
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        this.shutdown();
    }

}
