package com.test.sample.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.test.sample.BR
import com.test.sample.MyApplication
import com.test.sample.base.loading.LoadingView
import com.test.sample.base.loading.LoadingViewStatus
import com.test.sample.base.viewModel.BaseViewModel

/**
 * Date 2019/6/2 11:25 AM
 *
 * @author tangxin
 */
abstract class BaseFragment<T : ViewDataBinding, E : BaseViewModel>(var modelClass: Class<E>?) :
    Fragment(), LoadingViewStatus {

    companion object {
        const val TAG = "BaseFragment"
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

    override fun requestLoading(): Int {
        return -1
    }

    override fun retry() {
        println("调用重试")
    }

    override fun close() {}

    internal abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(layoutId, container, false)
        mBinding = DataBindingUtil.bind(fragmentView)!!
        if (null != modelClass) {
            viewModel = MyApplication.of().get(modelClass!!)
            mBinding.setVariable(BR.vm, viewModel)
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView.callBack(this)
        initView(savedInstanceState)
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return MyApplication.of().get(modelClass)
    }

    fun open(intent: Intent) {
        startActivity(intent)
    }

}
