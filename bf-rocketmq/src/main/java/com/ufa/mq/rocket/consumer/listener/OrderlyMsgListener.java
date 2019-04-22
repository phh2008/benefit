package com.ufa.mq.rocket.consumer.listener;


import com.google.common.base.Strings;
import com.ufa.mq.rocket.handler.OrderlyMessageHandler;
import com.ufa.mq.rocket.message.MessageDecoder;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 并发消息监听器
 *
 * @author phh
 * @version V1.0
 * @date 2019/4/22
 */
public class OrderlyMsgListener implements MessageListenerOrderly {

    private static final Logger logger = LoggerFactory.getLogger(OrderlyMsgListener.class);

    private Map<String, Map<String, OrderlyMessageHandler>> topicToTagHandler;

    public void init() {
        if (topicToTagHandler == null || topicToTagHandler.isEmpty()) {
            throw new IllegalArgumentException("You must config topicToTagHandlerList in OrderlyMsgListener");
        }
        for (Map.Entry<String, Map<String, OrderlyMessageHandler>> entry : topicToTagHandler.entrySet()) {
            String topic = entry.getKey();
            Map<String, OrderlyMessageHandler> tagToHandler = entry.getValue();
            if (tagToHandler == null || tagToHandler.isEmpty()) {
                throw new IllegalArgumentException("You must config handler for topic=" + topic);
            }
        }
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        List<ConsumeOrderlyStatus> results = new ArrayList<>(msgs.size());
        for (MessageExt message : msgs) {
            String topic = message.getTopic();
            Map<String, OrderlyMessageHandler> tagToHandler = topicToTagHandler.get(topic);
            if (tagToHandler == null || tagToHandler.isEmpty()) {
                String errMsg = "Can't find tagToHandler for topic=" + topic;
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            OrderlyMessageHandler messageHandler;
            String tag = message.getTags();
            if (Strings.isNullOrEmpty(tag)) {
                //tag为空时，判断是处理器是否为一个
                if (tagToHandler.size() != 1) {
                    String errMsg = "Message doesn't have tag value but tagToHandler size() > 1,topic=" + topic;
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
                Map.Entry<String, OrderlyMessageHandler> defaultHandler = tagToHandler.entrySet().iterator().next();
                if (!defaultHandler.getKey().equals("*")) {
                    String errMsg = "message tags is empty, but tagToHandler key is not *,topic=" + topic;
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
                messageHandler = defaultHandler.getValue();
            } else {
                messageHandler = tagToHandler.get(tag);
                if (messageHandler == null) {
                    String errMsg = "Can't find handler for topic=" + topic + ", tag=" + tag;
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
            MessageDecoder decoder = messageHandler.getMessageDecoder();
            results.add(messageHandler.process(decoder.decode(message.getBody()), message, context));
        }
        //如果有消息状态(重发)
        for (ConsumeOrderlyStatus st : results) {
            if (ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT == st) {
                return st;
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }

    public Map<String, Map<String, OrderlyMessageHandler>> getTopicToTagHandler() {
        return topicToTagHandler;
    }

    public void setTopicToTagHandler(Map<String, Map<String, OrderlyMessageHandler>> topicToTagHandler) {
        this.topicToTagHandler = topicToTagHandler;
    }

}
