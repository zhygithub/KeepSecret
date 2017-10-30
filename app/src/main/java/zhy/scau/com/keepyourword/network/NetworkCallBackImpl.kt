package zhy.scau.com.keepyourword.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ZhengHy on 2017-10-30.
 */
class NetworkCallBackImpl<T>:Callback<T> {

    lateinit var mINetWorkCallBack: INetWorkCallBack<T>

    constructor(iNetWorkCallBack: INetWorkCallBack<T>){
        mINetWorkCallBack = iNetWorkCallBack
    }

    override fun onFailure(call: Call<T>?, throwable: Throwable?) {
        mINetWorkCallBack.onFailedWithOriginData(-1, throwable?.message?: "nothing")
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        mINetWorkCallBack.onSuccess(response?.body())
    }
}