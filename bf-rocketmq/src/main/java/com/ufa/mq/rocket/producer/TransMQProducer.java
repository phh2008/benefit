package com.ufa.mq.rocket.producer;

import com.google.common.base.Strings;
import com.ufa.mq.rocket.exception.RocketMQException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> 事务消息发送者
 *
 * @author phh
 * @version V1.0
 * @date 2018/1/3
 */
public class TransMQProducer extends TransactionMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(TransMQProducer.class);


    /**
     * <p> 发送事务消息
     *
     * @param topic        主题
     * @param tags         标签(对主题细分)
     * @param body         消息内容
     * @param arg          自定义参数
     * @param tranExecuter 本地事务
     * @return TransactionSendResult
     * @throws MQClientException if error
     * @author phh
     * @date 2018/1/3
     * @version V1.0
     */
    public TransactionSendResult sendInTransaction(String topic, String tags, byte[] body, Object arg, LocalTransactionExecuter tranExecuter) throws MQClientException {
        Message msg = new Message();
        msg.setBody(body);
        msg.setTopic(topic);
        msg.setTags(tags);
        return this.sendMessageInTransaction(msg, tranExecuter, arg);
    }

    /**
     * <p> 开启
     * Spring bean init-method
     *
     * @throws RocketMQException if error
     * @author phh
     * @date 2018/1/3
     * @version V1.0
     */
    public void onStart() {
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
            logger.info(String.format("TransMQProducer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , this.getProducerGroup(), this.getNamesrvAddr()));
        } catch (MQClientException e) {
            logger.error(String.format("TransMQProducer is error %s", e.getMessage()), e);
            throw new RocketMQException(e);
        }
    }

    /**
     * <p> 关闭
     * Spring bean destroy-method
     *
     * @author phh
     * @date 2018/1/3
     * @version V1.0
     */
    public void onDestroy() {
        this.shutdown();
    }

}
