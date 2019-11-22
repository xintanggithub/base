package com.test.sample.base.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.test.sample.base.loading.LoadingViewStatus

/**
 * Date 2019/5/30 6:39 PM
 * 为了尽量分层，ViewModel不得实现任何直接对于view的操作,
 * 如果没有完全解耦的需要，可以在此处继承LoadingView，即可实现在viewModel内全部操作公共加载布局
 * In order to layer as much as possible, the ViewModel must not implement any operations
 * directly on the view. If there is no need for complete decoupling, you can inherit
 * the LoadingView here, and you can implement the common loading layout in the viewModel.
 * @author tangxin
 */
abstract class BaseViewModel : ViewModel() {

    var loadBaseStatus = ObservableField<LoadingViewStatus>()

}
