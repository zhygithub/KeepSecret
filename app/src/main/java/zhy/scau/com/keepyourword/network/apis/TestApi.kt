package zhy.scau.com.keepyourword.network.apis

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import zhy.scau.com.keepyourword.network.*
import zhy.scau.com.keepyourword.network.requests.TestRequest
import zhy.scau.com.keepyourword.network.responses.TestResponse

/**
 * Created by ZhengHy on 2017-10-26.
 */
class TestApi():BaseApi(){

    private var mCall: Call<TestResponse>? = null

    interface TestSevice{

        @GET("data/sk/{arg}.html")
        fun getTestData(@Path("arg") arg: String ): Call<TestResponse>
    }

    override fun getApiName(): String {
        return "TestApi"
    }

    override fun requestNet(requestCode: Int, requestData: BaseRequest, callBack: INetWorkCallBack<BaseResponse>) {
        var testService:TestSevice = mRetrofit.create(TestSevice::class.java)
        mCall = testService.getTestData((requestData as TestRequest).mData)
        mCall?.enqueue(NetworkCallBackImpl<TestResponse>(callBack as INetWorkCallBack<TestResponse>))
    }

    override fun cancel(requestCode: Int) {
        if(mCall != null){
            if(mCall!!.isExecuted){
                mCall!!.cancel()
                Log.d("netwwwww","cancel yes!")
            }
        }
    }


}