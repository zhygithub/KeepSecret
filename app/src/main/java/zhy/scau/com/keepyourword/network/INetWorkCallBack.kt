package zhy.scau.com.keepyourword.network

/**
 * Created by ZhengHy on 2017-10-26.
 */
interface INetWorkCallBack<T> {

    fun onSuccess(data: T)

    fun onFailed(failedCode: Int)

    fun onFailedWithOriginData(failedCode: Int, originData: String)
}