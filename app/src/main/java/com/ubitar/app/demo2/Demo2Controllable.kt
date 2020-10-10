package com.ubitar.app.demo2

import com.blankj.utilcode.util.ToastUtils
import com.ubitar.app.demo2.dialog.LoadingDialog
import com.ubitar.capybara.mvvm.activity.BaseActivity
import com.ubitar.capybara.mvvm.control.IControllable
import com.ubitar.capybara.mvvm.control.IController
import java.lang.ref.WeakReference

class Demo2Controllable : IControllable {

    private var loadingDialog: WeakReference<LoadingDialog>? = null

    override fun showLoading(controller: IController, isOutsideEnable: Boolean, isBackEnable: Boolean, onCanceledListener: (() -> Unit)?, extra: Array<out Any?>) {
        loadingDialog = WeakReference(LoadingDialog.Builder()
            .setOutsideCancelable(isOutsideEnable)
            .setBackEnable(isBackEnable)
            .setDismissListener {
                onCanceledListener?.invoke()
            }
            .build()
        )
        loadingDialog?.get()?.show((controller as BaseActivity<*, *>).supportFragmentManager, "loading")
    }

    override fun hideLoading() {
        loadingDialog?.get()?.dismiss()
    }

    override fun showMessage(text: String, extra: Array<out Any?>) {
        ToastUtils.showShort(text)
    }

    override fun showSuccess(text: String, extra: Array<out Any?>) {
    }

    override fun showFail(text: String, extra: Array<out Any?>) {
    }

    override fun onDestroy() {
        hideLoading()
        loadingDialog?.clear()
    }
}