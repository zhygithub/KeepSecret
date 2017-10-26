package zhy.scau.com.keepyourword.network

/**
 * Created by ZhengHy on 2017-10-26.
 */
interface INetWork<T, R> {
    fun requestNet(requestData: T, callBack: INetWorkCallBack<R>)
}