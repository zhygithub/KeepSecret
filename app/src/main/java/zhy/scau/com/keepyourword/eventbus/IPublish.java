package zhy.scau.com.keepyourword.eventbus;

import zhy.scau.com.keepyourword.eventbus.events.BaseEvent;

/**
 * Created by ZhengHy on 2017-09-18.
 */

public interface IPublish {
    void publishEvent(BaseEvent baseEvent);
}
