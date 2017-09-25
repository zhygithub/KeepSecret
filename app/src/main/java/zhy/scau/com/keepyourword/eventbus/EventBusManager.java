package zhy.scau.com.keepyourword.eventbus;

import org.greenrobot.eventbus.EventBus;

import zhy.scau.com.keepyourword.eventbus.events.BaseEvent;

/**
 * Created by ZhengHy on 2017-09-15.
 */

public class EventBusManager implements IPublish{

    private  EventBus mCore;

    private static EventBusManager INSTANCE;

    private EventBusManager(){
        mCore = EventBus.getDefault();
    }

    public static EventBusManager getInstance(){
        if(INSTANCE == null){
            synchronized (EventBusManager.class){
                if(INSTANCE == null){
                    INSTANCE = new EventBusManager();
                }
            }
        }
        return INSTANCE;
    }

    public void register(IRegistable registable){
        mCore.register(registable);
    }


    public void unregister(IRegistable registable){
        mCore.unregister(registable);
    }

    @Override
    public void publishEvent(BaseEvent baseEvent) {
        mCore.post(baseEvent);
    }
}
