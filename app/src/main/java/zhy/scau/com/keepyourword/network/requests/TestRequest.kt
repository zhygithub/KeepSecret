package zhy.scau.com.keepyourword.network.requests

import retrofit2.Retrofit
import zhy.scau.com.keepyourword.network.BaseRequest

/**
 * Created by ZhengHy on 2017-10-26.
 */
class TestRequest(var mData: String): BaseRequest(){
    override var mCallRequest: String = "TestRequest"
}