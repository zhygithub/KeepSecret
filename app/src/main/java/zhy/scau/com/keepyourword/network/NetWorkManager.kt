package zhy.scau.com.keepyourword.network

/**
 * Created by ZhengHy on 2017-10-26.
 */
class NetWorkManager: INetWork<BaseRequest, BaseResponse>{


    override fun requestNet(requestData: BaseRequest, callBack: INetWorkCallBack<BaseResponse>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        NetWorkCore.getInstance().request(requestData,callBack)
    }

}