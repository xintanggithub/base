package com.test.sample.test

import android.util.Log
import com.google.gson.Gson
import com.test.sample.R
import com.test.sample.base.activity.BaseActivity
import com.test.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

/**
 * Date 2019-11-22 20:54
 *
 * @author tangxin
 */
class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, TestViewModel>(TestViewModel::class.java) {

    override fun requestLoading() = R.id.loading

    val api = RetrofitFactory.createApi(Api::class.java)

    override fun initView() {
        mBinding.show.setOnClickListener {
            loadingView.show(this)
        }
        mBinding.hide.setOnClickListener {
            loadingView.hide(this)
        }
        mBinding.query.setOnClickListener {
            am({ getGm() })
        }
    }


    suspend fun getGm(): Gm {
        return api.getGmAsync(1, 10)
    }

    /**
     * 自定义方法 block ，还可以添加其他的方法，可带返回参数的，如 ： success: (T?) -> Unit = {}, fail: () -> Unit = {}
     */
    fun <T : Gm> am(block: suspend CoroutineScope.() -> T?) = GlobalScope.launch {
        try {
            withTimeout(50_000) {
                val res = withContext(Dispatchers.IO) {
                    block()
                }
                res.let {
                    Log.w("TAG_TEST", Gson().toJson(it))
                    //可以做逻辑异常判断   登录超时、返回数据为空等
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //运行时异常   404  500  indexOfException等
        }
    }


}
