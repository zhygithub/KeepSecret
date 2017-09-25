package zhy.scau.com.keepyourword.cache;

/**
 * Created by ZhengHy on 2017-09-18.
 */

public enum CacheType {


    NET_FIRST_CACHE_SECOND, //网络优先，其次取缓存的
    CACHE_FIRST_NET_SECOND, //三级缓存，缓存有限，最后网络
    CACHE_FIRST_NET_DELAY;  //直接先取缓存的，再延迟网络更新
}
