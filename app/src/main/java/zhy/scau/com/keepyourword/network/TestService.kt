package zhy.scau.com.keepyourword.network

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by ZhengHy on 2017-10-26.
 */
interface TestService {

    @GET("")
    fun getData(str: String):Call<BaseResponse>
}