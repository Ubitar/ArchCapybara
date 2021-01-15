package com.ubitar.capybara.mvvm.action

import android.os.Bundle
import com.weikaiyun.fragmentation.ISupportFragment
import java.lang.ref.WeakReference

open class FragmentActions {

    val onBackPressedSupportAction: OnBackPressedSupportAction by lazy { OnBackPressedSupportAction() }
    val postAction: PostAction by lazy { PostAction() }
    val setFragmentResultAction: SetFragmentResultAction by lazy { SetFragmentResultAction() }
    val putNewBundleAction: PutNewBundleAction by lazy { PutNewBundleAction() }
    val loadRootFragmentAction: LoadRootFragmentAction by lazy { LoadRootFragmentAction() }
    val startAction: StartAction by lazy { StartAction() }
    val startForResultAction: StartForResultAction by lazy { StartForResultAction() }
    val startWithPopAction: StartWithPopAction by lazy { StartWithPopAction() }
    val startWithPopToAction: StartWithPopToAction by lazy { StartWithPopToAction() }
    val replaceFragmentAction: ReplaceFragmentAction by lazy { ReplaceFragmentAction() }
    val popAction: PopAction by lazy { PopAction() }
    val popToAction: PopToAction by lazy { PopToAction() }
    val showLoadingAction: ShowLoadingAction by lazy { ShowLoadingAction() }
    val hideLoadingAction: HideLoadingAction by lazy { HideLoadingAction() }
    val showMessageAction: ShowMessageAction by lazy { ShowMessageAction() }
    val showSuccessAction: ShowSuccessAction by lazy { ShowSuccessAction() }
    val showFailAction: ShowFailAction by lazy { ShowFailAction() }

    val hideKeyboardAction: HideKeyboardAction by lazy { HideKeyboardAction() }

    class OnBackPressedSupportAction : SingleLiveAction<Any>() {
        override fun describe(): String {
            return "ISupportFragment.onBackPressedSupport()"
        }
    }

    class PopAction : SingleLiveAction<Any>() {
        override fun describe(): String {
            return "ISupportFragment.pop()方法"
        }

    }

    class PostAction : SingleLiveAction<Runnable>() {
        override fun describe(): String {
            return "Context.post()"
        }
    }

    class StartAction : SingleLiveAction<StartAction.Start>() {

        override fun describe(): String {
            return "ISupportFragment.start()"
        }

        data class Start(
                val toFragment: WeakReference<ISupportFragment>,
                val activityFragmentManager: Boolean = false,
                val launchMode: Int? = null
        )
    }

    class LoadRootFragmentAction : SingleLiveAction<LoadRootFragmentAction.LoadRootFragment>() {
        override fun describe(): String {
            return "Fragment.loadRootFragment()"
        }

        data class LoadRootFragment(
            val containerId: Int,
            val toFragment: WeakReference<ISupportFragment>
        )

    }

    class PopToAction : SingleLiveAction<PopToAction.PopTo>() {
        override fun describe(): String {
            return "ISupportFragment.popTo()方法"
        }

        data class PopTo(
            val targetFragmentClass: Class<*>,
            val includeTargetFragment: Boolean
        )

    }

    class PutNewBundleAction : SingleLiveAction<Bundle>() {

        override fun describe(): String {
            return "Fragment.putNewBundle()"
        }

    }

    class ReplaceFragmentAction : SingleLiveAction<ReplaceFragmentAction.ReplaceFragment>() {

        override fun describe(): String {
            return "ISupportFragment.replaceFragment()"
        }

        data class ReplaceFragment(
            val toFragment: WeakReference<ISupportFragment>
        )

    }

    class SetFragmentResultAction : SingleLiveAction<SetFragmentResultAction.SetFragmentResult>() {

        override fun describe(): String {
            return "Fragment.setFragmentResult()"
        }

        data class SetFragmentResult(
            val resultCode: Int,
            val data: Bundle
        )

    }

    class StartForResultAction : SingleLiveAction<StartForResultAction.StartForResult>() {

        override fun describe(): String {
            return "ISupportFragment.startForResult()"
        }

        data class StartForResult(
            val toFragment: WeakReference<ISupportFragment>,
            val requestCode: Int
        )

    }

    class StartWithPopAction : SingleLiveAction<WeakReference<ISupportFragment>>() {

        override fun describe(): String {
            return "ISupportFragment.startWithPop()"
        }

    }

    class StartWithPopToAction : SingleLiveAction<StartWithPopToAction.StartWithPopTo>() {

        override fun describe(): String {
            return "ISupportFragment.startWithPopTo()"
        }

        data class StartWithPopTo(
            val toFragment: WeakReference<ISupportFragment>,
            val targetFragmentClass: Class<*>,
            val includeTargetFragment: Boolean
        )

    }

    class ShowLoadingAction : SingleLiveAction<ShowLoadingAction.ShowLoading>() {

        override fun describe(): String {
            return "IController.showLoading()"
        }

        data class ShowLoading(
            val isCancelEnable: Boolean,
            val isBackEnable: Boolean,
            val dismissListener: (() -> Unit)?,
            val extra: Array<out Any?>
        )
    }

    class HideLoadingAction : SingleLiveAction<Any>() {

        override fun describe(): String {
            return "IController.hideLoading()"
        }

    }

    class ShowMessageAction : SingleLiveAction<ShowMessageAction.ShowMessage>() {
        override fun describe(): String {
            return "IController.showMessage()"
        }
        data class ShowMessage(
            val text: String,
            val onDismissListener:(()->Unit)?=null,
            val extra: Array<out Any?>
        )
    }

    class HideKeyboardAction : SingleLiveAction<Any>() {
        override fun describe(): String {
            return "BaseFragment.hideKeyBoard()"
        }
    }

    class ShowSuccessAction : SingleLiveAction<ShowSuccessAction.ShowSuccess>() {
        override fun describe(): String {
            return "IController.showSuccess()"
        }

        data class ShowSuccess(
            val text: String,
            val onDismissListener:(()->Unit)?=null,
            val extra: Array<out Any?>
        )
    }

    class ShowFailAction : SingleLiveAction<ShowFailAction.ShowFail>() {
        override fun describe(): String {
            return "IController.showFail()"
        }

        data class ShowFail(
            val text: String,
            val onDismissListener:(()->Unit)?=null,
            val extra: Array<out Any?>
        )
    }
}