package com.ubitar.app.demo4.network


import com.ubitar.app.common.Host
import com.ubitar.app.demo4.network.bean.UserBean
import com.ubitar.capybara.network.bean.IBaseResponse
import com.ubitar.capybara.network.repository.BaseRepository
import io.reactivex.Flowable

/**
 * XXRepository类中的方法主要是对网络请求做部分准备
 *
 *
 * 例如：你传入了一个Bean类，希望通过该Model类进行对类的拆解，取出Bean类中相应的值放入Api接口中作为参数
 *
 *
 * 例如2：你的传入参数需要进行排序签名，可以将代码放置到此处
 */

class Demo4Repository : BaseRepository<Demo4Api>() {

    override fun getApi(): Class<Demo4Api> {
        return Demo4Api::class.java
    }

    override fun getServerTag(): String {
        return Host.DEFAULT_HOST_URL
    }

    fun login(account: String, password: String): Flowable<UserBean> {
        return repository.login(account, password)
    }

    fun logout(token: String): Flowable<IBaseResponse> {
        return repository.logout(token)
    }


}