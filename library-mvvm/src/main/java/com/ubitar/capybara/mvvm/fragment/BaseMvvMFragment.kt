package com.ubitar.capybara.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ubitar.capybara.mvvm.control.IController
import com.ubitar.capybara.mvvm.IView
import com.ubitar.capybara.mvvm.vm.BaseFragmentViewModel
import com.weikaiyun.fragmentation.ISupportActivity
import com.weikaiyun.fragmentation.SupportActivity
import com.weikaiyun.fragmentation_swipeback.SwipeBackFragment
import java.lang.reflect.ParameterizedType

abstract class BaseMvvMFragment<V : ViewDataBinding, VM : BaseFragmentViewModel<*>> :
    SwipeBackFragment(), IView, IController {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //创建并获取Databinding对象
        binding = getDataBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //创建并获取ViewModel对象
        viewModel = getViewModel()

        //关联databinding和ViewModel，是数据驱动的关键
        binding.setVariable(getViewModelId(), viewModel)
        binding.lifecycleOwner = this

        //让ViewModel监听Fragment的生命周期
        viewModel.injectLifecycleOwner(this)
        //创建ViewModel后
        onCreatedViewModel()
        viewModel.injectBaseActions(viewModel.onCreateActions())
        // 注册内容监听前 */
        onBeforeObservable()
        //Activity注册ViewModel数据内容监听器，用于ViewModel通知View层的操作
        onBindObservable()
    }

    /**
     * 创建ViewModel后
     */
    override fun onCreatedViewModel() {
    }

    /**
     * 在注册ViewModel与View的回调事件前
     */
    override fun onBeforeObservable() {
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }


    /**
     * 注册ViewModel数据监听器
     */
    override fun onBindObservable() {
        viewModel.getBaseActions().onBackPressedSupportAction.observe(viewLifecycleOwner, Observer {
            (activity as ISupportActivity).onBackPressedSupport()
        })
        viewModel.getBaseActions().postAction.observe(viewLifecycleOwner, Observer {
            post(it)
        })
        viewModel.getBaseActions().setFragmentResultAction.observe(viewLifecycleOwner, Observer {
            setFragmentResult(it.resultCode, it.data)
        })
        viewModel.getBaseActions().putNewBundleAction.observe(viewLifecycleOwner, Observer {
            putNewBundle(it)
        })
        viewModel.getBaseActions().loadRootFragmentAction.observe(viewLifecycleOwner, Observer {
             loadRootFragment(it.containerId, it.toFragment)
        })
        viewModel.getBaseActions().startAction.observe(viewLifecycleOwner, Observer {
            if (it.launchMode == null) {
                if (it.activityFragmentManager)
                    (requireActivity() as SupportActivity).start(it.toFragment)
                else start(it.toFragment)
            } else {
                if (it.activityFragmentManager)
                    (requireActivity() as SupportActivity).start(it.toFragment, it.launchMode)
                else start(it.toFragment, it.launchMode)
            }
        })
        viewModel.getBaseActions().startForResultAction.observe(viewLifecycleOwner, Observer {
            startForResult(it.toFragment, it.requestCode)
        })
        viewModel.getBaseActions().startWithPopAction.observe(viewLifecycleOwner, Observer {
            startWithPop(it)
        })
        viewModel.getBaseActions().startWithPopToAction.observe(viewLifecycleOwner, Observer {
            startWithPopTo(it.toFragment, it.targetFragmentClass, it.includeTargetFragment)
        })
        viewModel.getBaseActions().replaceFragmentAction.observe(viewLifecycleOwner, Observer {
            replaceFragment(it.toFragment)
        })
        viewModel.getBaseActions().popAction.observe(viewLifecycleOwner, Observer {
            pop()
        })
        viewModel.getBaseActions().popToAction.observe(viewLifecycleOwner, Observer {
            popTo(it.targetFragmentClass, it.includeTargetFragment)
        })
        viewModel.getBaseActions().showLoadingAction.observe(viewLifecycleOwner, Observer {
            showLoading(it.isCancelEnable, it.isBackEnable, it.dismissListener, it.extra)
        })
        viewModel.getBaseActions().hideLoadingAction.observe(viewLifecycleOwner, Observer {
            hideLoading()
        })
        viewModel.getBaseActions().showMessageAction.observe(viewLifecycleOwner, Observer {
            showMessage(it.text, it.onDismissListener, it.extra)
        })
        viewModel.getBaseActions().showSuccessAction.observe(viewLifecycleOwner, Observer {
            showSuccess(it.text, it.onDismissListener, it.extra)
        })
        viewModel.getBaseActions().showFailAction.observe(viewLifecycleOwner, Observer {
            showFail(it.text, it.onDismissListener, it.extra)
        })
    }

    /**
     * 创建并获取DataBinding
     */
    override fun <T : ViewDataBinding> getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T {
        return DataBindingUtil.inflate(
            inflater,
            getLayoutId(inflater, savedInstanceState),
            container,
            false
        )
    }


    /**
     * 创建并获取ViewModel实例
     * */
    override fun <T : ViewModel> getViewModel(): T {
        val type = this.javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            AndroidViewModel::class.java
        } as Class<T>
        return ViewModelProvider(this).get(modelClass)
    }

}