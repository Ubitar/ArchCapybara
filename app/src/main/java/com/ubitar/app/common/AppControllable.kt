package com.ubitar.app.common

import com.blankj.utilcode.util.ToastUtils
import com.ubitar.app.demo6.myxpopup.LoadingPopup
import com.ubitar.capybara.mvvm.control.IControllable
import com.ubitar.capybara.mvvm.control.IController
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import java.lang.ref.WeakReference

class AppControllable : IControllable {
    private var loadingPopup: WeakReference<BasePopupView>? = null

    override fun showLoading(controller: IController, isOutsideEnable: Boolean, isBackEnable: Boolean, onCanceledListener: (() -> Unit)?, extra: Array<out Any?>) {
        loadingPopup?.get()?.dismiss()
        loadingPopup = WeakReference(
            XPopup.Builder(controller.getContext())
                .hasShadowBg(false)
                .dismissOnTouchOutside(isOutsideEnable)
                .dismissOnBackPressed(isBackEnable)
                .setPopupCallback(object : SimpleCallback() {
                    override fun onBackPressed(): Boolean {
                        onCanceledListener?.invoke()
                        return false
                    }
                })
                .asCustom(LoadingPopup.Builder().setText("加载中").build(controller))
                .show())    }

    override fun hideLoading() {
        loadingPopup?.get()?.dismiss()
    }

    override fun showMessage(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
        ToastUtils.showShort(text)
    }

    override fun showSuccess(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
    }

    override fun showFail(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
    }

    override fun onDestroy() {
        hideLoading()
        loadingPopup?.clear()
    }
}