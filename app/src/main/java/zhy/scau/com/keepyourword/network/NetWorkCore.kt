package zhy.scau.com.keepyourword.network

import retrofit2.Call
import retrofit2.Retrofit

/**
 * Created by ZhengHy on 2017-10-26.
 */
object NetWorkCore {

    lateinit var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build()
    }

    fun getInstance(): NetWorkCore{
        return this
    }

    fun request(request: BaseRequest, callBack: INetWorkCallBack<BaseResponse> ){
        var requestType:String = request.getRequestType()

        var data:BaseResponse?
        when(requestType){
            "a" -> {
                val data:Call<BaseResponse> = retrofit.create(TestService::class.java).getData("")
            }

        }

    }
}