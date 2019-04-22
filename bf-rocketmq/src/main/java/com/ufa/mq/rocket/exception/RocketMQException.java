package com.ufa.mq.rocket.exception;

/**
 * rocketmq 异常处理
 * Created by phh on 2017/11/21.
 * @author phh
 */
public class RocketMQException extends RuntimeException{


    public RocketMQException() {
        super();
    }

    public RocketMQException(String message) {
        super(message);
    }

    public RocketMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public RocketMQException(Throwable cause) {
        super(cause);
    }

    protected RocketMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
