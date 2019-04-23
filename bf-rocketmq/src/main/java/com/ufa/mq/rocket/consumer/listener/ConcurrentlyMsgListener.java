package com.ufa.mq.rocket.consumer.listener;


import com.google.common.base.Strings;
import com.ufa.mq.rocket.handler.ConcurrentlyMessageHandler;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
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
public class ConcurrentlyMsgListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentlyMsgListener.class);

    private Map<String, Map<String, ConcurrentlyMessageHandler>> topicToTagHandler;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        List<ConsumeConcurrentlyStatus> results = new ArrayList<>(msgs.size());
        for (MessageExt message : msgs) {
            String topic = message.getTopic();
            Map<String, ConcurrentlyMessageHandler> tagToHandler = topicToTagHandler.get(topic);
            if (tagToHandler == null || tagToHandler.isEmpty()) {
                String errMsg = "Can't find tagToHandler for topic=" + topic;
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            ConcurrentlyMessageHandler messageHandler;
            String tag = message.getTags();
            if (Strings.isNullOrEmpty(tag)) {
                //tag为空时，判断是处理器是否为一个
                if (tagToHandler.size() != 1) {
                    String errMsg = "Message doesn't have tag value but tagToHandler size() > 1,topic=" + topic;
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
                Map.Entry<String, ConcurrentlyMessageHandler> defaultHandler = tagToHandler.entrySet().iterator().next();
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
            results.add(messageHandler.process(messageHandler.decode(message.getBody()), message, context));
        }
        //如果有消息状态(重发)
        for (ConsumeConcurrentlyStatus st : results) {
            if (ConsumeConcurrentlyStatus.RECONSUME_LATER == st) {
                return st;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public Map<String, Map<String, ConcurrentlyMessageHandler>> getTopicToTagHandler() {
        return topicToTagHandler;
    }

    public void setTopicToTagHandler(Map<String, Map<String, ConcurrentlyMessageHandler>> topicToTagHandler) {
        this.topicToTagHandler = topicToTagHandler;
    }
}
