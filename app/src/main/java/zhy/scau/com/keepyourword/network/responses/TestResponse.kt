package zhy.scau.com.keepyourword.network.responses

import com.google.gson.annotations.SerializedName
import zhy.scau.com.keepyourword.network.BaseResponse

/**
 * Created by ZhengHy on 2017-10-30.
 */
class TestResponse():BaseResponse() {

    lateinit var weatherinfo: WeatherData


    class WeatherData{

        var city:String = ""

        var cityid:String = ""

        var temp:String = ""

        var WD:String = ""

        var SD:String = ""

        var WSE: String = ""

        @SerializedName("time") var aTime: String = ""

        @SerializedName("isRadar") var aIsRadar: String = ""

        @SerializedName("Radar") var aRadar: String =""

        var njd: String =""

        var qy: String =""

        var rain: String =""
        override fun toString(): String {
            return "WeatherData(city='$city', cityid='$cityid', temp='$temp', WD='$WD', SD='$SD', WSE='$WSE', aTime='$aTime', aIsRadar='$aIsRadar', aRadar='$aRadar', njd='$njd', qy='$qy', rain='$rain')"
        }


    }

    override fun toString(): String {
        return "TestResponse(weatherinfo=$weatherinfo)" + weatherinfo.toString()
    }
}