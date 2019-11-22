package com.test.sample.base.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.test.sample.BR
import com.test.sample.MyApplication
import com.test.sample.base.loading.LoadingView
import com.test.sample.base.loading.LoadingViewStatus
import com.test.sample.base.viewModel.BaseViewModel

/**
 *  Date 2019-09-19 17:16
 *
 * @author tangxin
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(private var modelClass: Class<E>) :
    FragmentActivity(),
    LoadingViewStatus {

    companion object {
        const val TAG = "BaseActivity"
    }

    lateinit var mBinding: T

    lateinit var viewModel: E

    var loadingView = LoadingView()

    /**
     * get layoutId
     *
     * @return layoutId
     */
    internal abstract val layoutId: Int

    /**
     * loading finished
     */
    internal abstract fun initView()

    override fun requestLoading(): Int {
        return -1
    }

    /**
     * Implement this method if there is a retry
     */
    override fun retry() {
        println("调用重试")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = MyApplication.of().get(modelClass)
        viewModel.loadBaseStatus.set(this)
        // ↓ ---> Be sure to have a viewModel called vm first.
        mBinding.setVariable(BR.vm, viewModel)
        loadingView.callBack(this)
        initView()
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return MyApplication.of().get(modelClass)
    }

    /**
     * If you do not need to use the default listenback back event method, you can re-implement, not super
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            close()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun open(intent: Intent) {
        startActivity(intent)
    }

    override fun close() {
        finish()
    }
}
