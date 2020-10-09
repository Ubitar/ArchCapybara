package com.ubitar.app.demo6.vm

import android.app.Application
import com.ubitar.app.demo6.action.PopupActions
import com.ubitar.capybara.mvvm.model.BaseModel
import com.ubitar.capybara.mvvm.vm.base.BaseViewModel

abstract class BasePopupViewModel<M : BaseModel>(application: Application) :
    BaseViewModel<M>(application) {

    private lateinit var baseActions: PopupActions

    fun post(runnable: Runnable) {
        baseActions.postAction.call(runnable)
    }

    fun dismiss() {
        baseActions.dismissAction.call()
    }

    fun show() {
        baseActions.showAction.call()
    }

    fun getBaseActions(): PopupActions {
        return baseActions
    }

    fun injectBaseActions(actions: PopupActions?) {
        baseActions = actions?:PopupActions()
    }

    /** 创建自己的Action，并继承PopupActions */
    abstract fun onCreateActions(): PopupActions?

}