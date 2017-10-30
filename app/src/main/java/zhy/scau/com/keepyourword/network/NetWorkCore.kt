package zhy.scau.com.keepyourword.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import zhy.scau.com.keepyourword.network.apis.TestApi
import zhy.scau.com.keepyourword.network.requests.TestRequest
import zhy.scau.com.keepyourword.network.responses.TestResponse

/**
 * Created by ZhengHy on 2017-10-26.
 */
object NetWorkCore: INetWork{


    lateinit var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build()
    }

    fun getInstance(): NetWorkCore{
        return this
    }


    override fun requestNet(requestCode: Int, requestData: BaseRequest, callBack: INetWorkCallBack<BaseResponse>) {
        var baseApi: BaseApi?
        when(requestData.mCallRequest){
            "TestRequest" -> {
                baseApi = TestApi()
            }
            else -> baseApi = null
        }

        baseApi?.requestNet(requestCode, requestData as TestRequest, callBack)
    }

}