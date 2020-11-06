package com.ubitar.capybara.mvvm.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import com.ubitar.capybara.mvvm.R
import com.ubitar.capybara.mvvm.common.ActivityManager
import com.ubitar.capybara.mvvm.control.ControlProvider
import com.ubitar.capybara.mvvm.dispatcher.OnActivityResultDispatcher
import com.ubitar.capybara.mvvm.dispatcher.OnKeyDownDispatcher
import com.ubitar.capybara.mvvm.dispatcher.OnKeyUpDispatcher
import com.ubitar.capybara.mvvm.vm.base.BaseActivityViewModel

abstract class BaseActivity<V : ViewDataBinding, VM : BaseActivityViewModel<*>> :
    BaseMvvMActivity<V, VM>() {

    protected lateinit var controllerProvider: ControlProvider

    private val onActivityResultDispatcher = OnActivityResultDispatcher()
    private val onKeyDownDispatcher = OnKeyDownDispatcher()
    private val onKeyUpDispatcher = OnKeyUpDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.getManager().addActivity(this)
        controllerProvider = ControlProvider(this)
        initParams()
        initViewModelParams()
        initView()
        viewModel.initEvent(this)
        viewModel.initData()
    }

    override fun onCreatedViewModel() {
        super.onCreatedViewModel()
        initDaggerInject()
    }

    override fun onBeforeObservable() {
        super.onBeforeObservable()
    }

    override fun onDestroyController() {
        controllerProvider.get().onDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.getManager().finishActivity(this)
    }

    override fun finish() {
        super.finish()
        val enterAnim = intent.getIntExtra(CUSTOM_POP_ENTER_TRANSITION_ANIMATION_TAG, R.anim.h_fragment_pop_enter)
        val exitAnim = intent.getIntExtra(CUSTOM_EXIT_TRANSITION_ANIMATION_TAG, R.anim.h_fragment_exit)
        overridePendingTransition(enterAnim, exitAnim)
    }

    //使onActivityResult能够传到fragment
    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultDispatcher.onActivityResult(supportFragmentManager, requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val isPropagated = onKeyDownDispatcher.onKeyDown(supportFragmentManager, keyCode, event)
        if (isPropagated) return isPropagated
        else return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val isPropagated = onKeyUpDispatcher.onKeyUp(supportFragmentManager, keyCode, event)
        if (isPropagated) return isPropagated
        else return super.onKeyUp(keyCode, event)
    }

    fun getOnActivityResultDispatcher(): OnActivityResultDispatcher {
        return onActivityResultDispatcher
    }

    override fun showLoading(isOutsideEnable: Boolean, isBackEnable: Boolean, onCanceledListener: (() -> Unit)?, extra: Array<out Any?>) {
        controllerProvider.get().showLoading(this, isOutsideEnable, isBackEnable, onCanceledListener, extra)
    }

    override fun showSuccess(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
        controllerProvider.get().showSuccess(text, onDismissListener, extra)
    }

    override fun showFail(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
        controllerProvider.get().showFail(text, onDismissListener, extra)
    }

    override fun hideLoading() {
        controllerProvider.get().hideLoading()
    }

    override fun showMessage(text: String, onDismissListener: (() -> Unit)?, extra: Array<out Any?>) {
        controllerProvider.get().showMessage(text, onDismissListener, extra)
    }

    override fun getContext(): Context? {
        return this
    }

    fun getActivity(): BaseActivity<V, VM> {
        return this
    }

    /** 初始化页面参数  */
    open fun initParams() {

    }

    /** Dagger注入 */
    open fun initDaggerInject() {

    }

    /** 初始化或传递ViewModel的参数  */
    open fun initViewModelParams() {

    }

    /** 初始化视图  */
    open fun initView() {

    }


    companion object {
        const val CUSTOM_POP_ENTER_TRANSITION_ANIMATION_TAG = "custom_pop_enter_transition_animation"
        const val CUSTOM_EXIT_TRANSITION_ANIMATION_TAG = "custom_exit_transition_animation"
    }

}