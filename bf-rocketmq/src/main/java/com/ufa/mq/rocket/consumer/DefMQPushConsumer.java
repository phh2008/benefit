package com.ufa.mq.rocket.consumer;

import com.ufa.mq.rocket.consumer.listener.ConcurrentlyMsgListener;
import com.ufa.mq.rocket.consumer.listener.OrderlyMsgListener;
import com.ufa.mq.rocket.exception.RocketMQException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * push消息消费者
 * Created by phh on 2017/11/21.
 *
 * @author phh
 */
public class DefMQPushConsumer extends DefaultMQPushConsumer {

    /**
     * 日志
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(DefMQPushConsumer.class);

    /**
     * Spring bean init-method
     */
    public void init() {
        try {
            //注册消息处理
            MessageListener listener = this.getMessageListener();
            this.registerMessageListener(listener);
            BiConsumer<String, Map<String, ?>> func = (topic, handlers) -> {
                try {
                    Set<String> tagsSet = handlers.keySet();
                    if (tagsSet.isEmpty()) {
                        this.subscribe(topic, "*");
                        LOGGER.info("Subscribe topic={},pattern={}", topic, "*");
                    } else {
                        String tag = String.join(" || ", tagsSet);
                        this.subscribe(topic, tag);
                        LOGGER.info("Subscribe topic={},pattern={}", topic, tag);
                    }
                } catch (MQClientException e) {
                    throw new RuntimeException(e.getErrorMessage());
                }
            };
            if (listener instanceof ConcurrentlyMsgListener) {
                ((ConcurrentlyMsgListener) listener).getTopicToTagHandler().forEach(func);
            } else if (listener instanceof OrderlyMsgListener) {
                ((OrderlyMsgListener) listener).getTopicToTagHandler().forEach(func);
            } else {
                LOGGER.error("messageListener must instance of ConcurrentlyMsgListener or OrderlyMsgListener");
                throw new RuntimeException("messageListener must instance of ConcurrentlyMsgListener or OrderlyMsgListener");
            }
            //启动
            this.start();
            LOGGER.info("DefMQPushConsumer is start !!! groupName:{},namesrvAddr:{}", this.getConsumerGroup(), this.getNamesrvAddr());
        } catch (Exception e) {
            LOGGER.error("DefMQPushConsumer is start !!! groupName:{},namesrvAddr:{}", this.getConsumerGroup(), this.getNamesrvAddr(), e);
            throw new RocketMQException(e.getMessage());
        }
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        this.shutdown();
    }

}
