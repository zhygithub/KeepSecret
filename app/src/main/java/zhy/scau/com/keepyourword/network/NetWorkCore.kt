package zhy.scau.com.keepyourword.network

import zhy.scau.com.keepyourword.network.apis.TestApi
import zhy.scau.com.keepyourword.network.requests.TestRequest

/**
 * Created by ZhengHy on 2017-10-26.
 */
object NetWorkCore: INetWork{

    var mApiList: HashMap<Int,BaseApi?> = HashMap()


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

        addRequestToEnqueue(requestCode, baseApi)

        var listener: INetWorkCallBack<BaseResponse> = object:INetWorkCallBack<BaseResponse>{
            override fun onFailedWithOriginData(failedCode: Int, originData: String) {
                mApiList.remove(requestCode)
                callBack.onFailedWithOriginData(failedCode, originData)
            }

            override fun onSuccess(data: BaseResponse?) {
                mApiList.remove(requestCode)
                callBack.onSuccess(data)
            }

        }
        baseApi?.requestNet(requestCode, requestData as TestRequest, listener)
    }


    override fun cancel(requestCode: Int) {
        var baseApi: BaseApi? = mApiList.get(requestCode)

        baseApi?.cancel(requestCode)
    }

    fun addRequestToEnqueue(requestCode: Int, baseApi: BaseApi?){
        if(mApiList.get(requestCode) != null){
            val api:BaseApi? = mApiList.get(requestCode)
            api!!.cancel(requestCode)
        }
        mApiList.put(requestCode, baseApi)

    }

}