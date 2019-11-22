package com.test.sample.test

import com.test.sample.R
import com.test.sample.base.activity.BaseActivity
import com.test.sample.databinding.ActivityMainBinding

/**
 * Date 2019-11-22 20:54
 *
 * @author tangxin
 */
class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, TestViewModel>(TestViewModel::class.java) {

    override fun requestLoading() = R.id.loading

    override fun initView() {
        mBinding.show.setOnClickListener {
            loadingView.show(this)
        }
        mBinding.hide.setOnClickListener {
            loadingView.hide(this)
        }
    }

}
