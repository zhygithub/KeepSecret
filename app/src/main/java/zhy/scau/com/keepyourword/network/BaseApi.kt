package zhy.scau.com.keepyourword.network

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ZhengHy on 2017-10-30.
 */
abstract open class BaseApi(): INetWork {


    var mBaseUrl: String = "http://www.weather.com.cn/"

    var mRetrofit: Retrofit


    init {
        mRetrofit = Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    constructor(baseUrl: String):this(){
        mBaseUrl = baseUrl
    }

    open fun getApiName():String {
        return ""
    }


}