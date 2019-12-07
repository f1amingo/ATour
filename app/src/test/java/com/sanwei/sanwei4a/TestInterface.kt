package com.sanwei.sanwei4a
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.entity.parameter.account.User
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.Area
import com.sanwei.sanwei4a.entity.po.Order
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.*
import com.sanwei.sanwei4a.model.order.OrderService
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*
open class TestInterface{
    /**
     * 对省市查询
     *
    /*val areaFindService=AreaFindService(this)
    override fun onSuccess(areas: List<Area>) {
        println("onSuccess")
        for(area in areas){
            println(area.toString())
        }
    }
    override fun onFail(msg: String?) {
        println("onFail")
        println(msg)
    }
    @Test
    fun testFindProvince(){
        areaFindService.request(0,Area.COUNTRY)
        Thread.sleep(100000)
    }
    @Test
    fun testFindCity(){
        areaFindService.request(360000,Area.PROVINCE)
        Thread.sleep(100000)
    }@Test
    fun testFindArea(){
        areaFindService.request(360800,Area.CITY)
        Thread.sleep(100000)
    }*/
    */
    /**
     * 对书籍查询
     * {"booBinding":"精装","bookAuthor":"\\[美]沃伦·巴菲特","bookDetail":"众所周知，巴菲特每年都会亲自执笔给伯克希尔的股东写一封信，迄今已写了52年。每一封致股东信都洋洋洒洒数万言，信中回顾公司业绩、投资策略，还会就许多热点话题表达观点。\n1996年，巴菲特授权劳伦斯·坎宁安教授编撰他的信件，并出版了超级畅销书《巴菲特致股东的信》，书中全部文字原汁原味地保留巴菲特第一人称的叙述，将其投资思想与管理智慧分为公司治理、财务与投资、投资替代品、普通股、兼并与收购、估值与会计、税务等主题。从此坎宁安教授成为巴菲特的御用编辑，并得到巴菲特的授权进入伯克希尔进行深度调研采访，并创作姊妹篇《超越巴菲特的伯克希尔》一书，专门论述伯克希尔的经营管理之道。\n相较于前三版，《巴菲特致股东的信：投资者和公司高管教程》第4版保留了原来的架构和哲学，增加了巴菲特最新的年报内容。这些新增的内容被编入了书中的相应篇章，它们有机地融为一体，丝毫不影响读者在阅读过程中获得对于稳健企业和投资哲学的整体印象。为了帮助读者区分书中哪些部分是新增的内容，在书末的“注释”部分标注了这些内容是选自哪些年度的致股东的信件。\n20年来，本书四版获得了无数赞誉，但最有价值的莫过于巴菲特本人的评价：“坎宁安做了一项伟大的工作，他整理呈现了我们的理念。这本书比任何一本关于我的传记都要好，如果让我选一本书去读，那必定是这一本。 ”",
     * "bookIsbn":"9787111592105","bookName":"巴菲特致股东的信","bookPage":472,"bookPrice":99.0,"bookPublisher":"机械工业出版社"}*/

    /**
     * 添加地址测试
     */
    @Test
    fun testAddAddress(){
        App.account = Account()
        App.account!!.accId = 4
        val service = AccountService()
        service.getBalance(object :OnRequestListener<Int>{
            override fun onSuccess(data: Int) {
                println(data)
            }

            override fun onFail(msg: String?, code: Int) {
                println("Fail")
                println(msg)
            }

        })
        Thread.sleep(5000)
    }

    /**
     * 查询地点名称测试
     */
    @Test
    fun testQueryCityName(){
        val accountService = AccountService()
        accountService.queryCityName(420000, Area.PROVINCE,object : OnRequestListener<String> {
            override fun onSuccess(name: String) {
                println(name)
            }
            override fun onFail(msg: String?,code:Int) {
                println(msg)
            }
        })
        Thread.sleep(5000)
    }

    @Test
    fun testFindArea(){
        /*val areaFindService = AreaFindService()
        areaFindService.request(110000,2,object : IAreaModel{
            override fun onSuccess(areas: List<Area>) {
                for(a in areas){
                    println("${a.code} ${a.name}")
                }
            }

            override fun onFail(msg: String?) {
                println(msg)
            }

        })*/
    }

    @Test
    fun testCreateOrder(){
        val order = Order()
        order.orderBooId = 8
        order.orderFromAid = 5  //订单发起者
        order.orderToAid = 4  //书籍拥有者
        order.orderPaytype = 1
        order.orderExtra = "这是一条测试订单"
        val orderServiec = OrderService()
        orderServiec.createOrder(order,object :OnRequestListener<Order>{
            override fun onSuccess(order: Order) {
                println(order.toString())
            }
            override fun onFail(msg: String?, code: Int) {
                println("$msg $code")
            }

        })
        Thread.sleep(5000)
    }

    @Test
    fun testPay(){
        val order = Order()
        order.orderIdx = "SW438652178949869568"
        order.orderBooId = 8
        order.orderFromAid = 5
        order.orderTime = Date()
        order.orderEndtime = Date(order.orderTime.time+1000*60*60*24*5)
        order.orderToAid = 4
        order.orderPaytype = 1
        order.orderExtra = "这是一条测试订单"
        val orderServiec = OrderService()
        orderServiec.pay(order,object : OnRequestListener<String?>{
            override fun onSuccess(msg:String?) {
                println("OK")
            }

            override fun onFail(msg: String?, code: Int) {
                println("$msg $code")
            }
        })
    }

    @Test
    fun testQueryInfo(){
        val accountService = AccountService()

        accountService.queryUserInfo(object : OnRequestListener<User>{
            override fun onSuccess(data: User) {
                print(data.toString())
            }

            override fun onFail(msg: String?, code: Int) {
                print("$msg $code")
            }

        })
/*        val str = "{\"code\":200,\"msg\":null,\"data\":{\"uId\":2,\"uAccid\":4,\"uIdnum\":\"1235456\",\"uName\":\"李昊江\",\"uSex\":\"男\",\"uSchool\":\"武汉大学\",\"uCollege\":\"遥感信息工程学院\",\"uSid\":\"123456\"}}"
        val result = JSONObject.parseObject(str,object : TypeReference<Result<User>>(){})
        println(result)*/
    }



}
