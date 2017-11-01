package zhy.scau.com.keepyourword.network

/**
 * Created by ZhengHy on 2017-10-26.
 */
interface INetWork {
    fun requestNet(requestCode: Int, requestData: BaseRequest, callBack: INetWorkCallBack<BaseResponse>)

    fun cancel(requestCode: Int)
}