package com.ubitar.capybara.mvvm.vm

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ubitar.capybara.mvvm.action.FragmentActions
import com.ubitar.capybara.mvvm.model.BaseModel
import com.weikaiyun.fragmentation.ISupportFragment
import java.lang.ref.WeakReference

abstract class BaseFragmentViewModel<M : BaseModel>(application: Application) :
    BaseViewModel<M>(application) {

    private lateinit var baseActions: FragmentActions

    fun onBackPressedSupport() {
        baseActions.onBackPressedSupportAction.call()
    }

    fun post(runnable: Runnable) {
        baseActions.postAction.call(runnable)
    }

    /**
     * 类似 [Activity.setResult]
     *
     *
     * Similar to [Activity.setResult]
     *
     * @see .startForResult
     */
    fun setFragmentResult(resultCode: Int, bundle: Bundle) {
        baseActions.setFragmentResultAction.call(
            FragmentActions.SetFragmentResultAction.SetFragmentResult(
                resultCode,
                bundle
            )
        )
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see .start
     */
    fun putNewBundle(newBundle: Bundle) {
        baseActions.putNewBundleAction.call(newBundle)
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        baseActions.loadRootFragmentAction.call(
            FragmentActions.LoadRootFragmentAction.LoadRootFragment(
                containerId,
                    WeakReference(toFragment)
            )
        )
    }

    fun start(toFragment: ISupportFragment) {
        baseActions.startAction.call(FragmentActions.StartAction.Start(WeakReference(toFragment)))
    }

    fun start(toFragment: ISupportFragment, activityFragmentManager: Boolean) {
        baseActions.startAction.call(
            FragmentActions.StartAction.Start(
                    WeakReference(toFragment),
                activityFragmentManager
            )
        )
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    fun start(
        toFragment: ISupportFragment,
        activityFragmentManager: Boolean,
        @ISupportFragment.LaunchMode launchMode: Int
    ) {
        baseActions.startAction.call(
            FragmentActions.StartAction.Start(
                    WeakReference(toFragment),
                activityFragmentManager,
                launchMode
            )
        )
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    fun startForResult(toFragment: ISupportFragment, requestCode: Int) {
        baseActions.startForResultAction.call(
            FragmentActions.StartForResultAction.StartForResult(
                    WeakReference(toFragment),
                requestCode
            )
        )
    }

    /**
     * Start the target Fragment and pop itself
     */
    fun startWithPop(toFragment: ISupportFragment) {
        baseActions.startWithPopAction.call(toFragment)
    }

    /**
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(
        toFragment: ISupportFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    ) {
        baseActions.startWithPopToAction.call(
            FragmentActions.StartWithPopToAction.StartWithPopTo(
                    WeakReference(toFragment),
                targetFragmentClass,
                includeTargetFragment
            )
        )
    }

    fun replaceFragmentAction(toFragment: ISupportFragment) {
        baseActions.replaceFragmentAction.call(
            FragmentActions.ReplaceFragmentAction.ReplaceFragment(
                    WeakReference(toFragment)
            )
        )
    }

    fun pop() {
        baseActions.popAction.call()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     *
     *
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        baseActions.popToAction.call(
            FragmentActions.PopToAction.PopTo(
                targetFragmentClass,
                includeTargetFragment
            )
        )
    }


    fun showLoading(
        isCancelEnable: Boolean = false,
        isBackEnable: Boolean = true,
        dismissListener: (() -> Unit)? = null,
        vararg extra: Any?
    ) {
        baseActions.showLoadingAction.call(
            FragmentActions.ShowLoadingAction.ShowLoading(
                isCancelEnable,
                isBackEnable,
                dismissListener,
                extra
            )
        )
    }

    fun hideLoading() {
        baseActions.hideLoadingAction.call()
    }


    fun showMessage(
        text: String,
        onDismissListener:(()->Unit)?=null,
        vararg extra: Any?
    ) {
        baseActions.showMessageAction.call(FragmentActions.ShowMessageAction.ShowMessage(text,onDismissListener,extra))
    }

    fun showSuccess(
        text: String,
        onDismissListener:(()->Unit)?=null,
        vararg extra: Any?
    ) {
        baseActions.showSuccessAction.call(
            FragmentActions.ShowSuccessAction.ShowSuccess(text,onDismissListener, extra)
        )
    }

    fun showFail(
        text: String,
        onDismissListener:(()->Unit)?=null,
        vararg extra: Any?
    ) {
        baseActions.showFailAction.call(
            FragmentActions.ShowFailAction.ShowFail(text,onDismissListener, extra)
        )
    }
    /**
     * 收起键盘
     */
    fun hideKeyboard() {
        baseActions.hideKeyboardAction.call()
    }

    fun getBaseActions(): FragmentActions {
        return baseActions
    }

    fun injectBaseActions(actions: FragmentActions?) {
        baseActions = actions ?: FragmentActions()
    }

    /** 创建自己的Action，并继承FragmentActions */
    abstract fun onCreateActions(): FragmentActions?

}