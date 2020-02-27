package com.test.sample.test

import android.util.Log
import com.google.gson.GsonBuilder
import com.test.sample.test.pk.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Date 2020-02-27 15:08
 *
 * @author Tson
 */
class RetrofitFactory {

    companion object {
        const val TAG = "RetrofitFactory"

        private val mLoggingInterceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("X-AUTH-DEVICE-TYPE", "android")
                .method(original.method(), original.body())
                .build()
            val response = chain.proceed(request)
            Log.i(
                TAG, String.format(
                    "Sending request: %s on %s%n%s",
                    request.url(),
                    chain.connection(),
                    request.headers()
                )
            )
            response
        }

        fun buildOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(60, TimeUnit.SECONDS)//读取超时
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时
            builder.addInterceptor(mLoggingInterceptor)//添加日志拦截器：显示链接信息
            return builder.build()
        }

        fun <T> createApi(service: Class<T>): T {
            return createApi(service, "https://tsondy.club/")
        }

        fun <T> createApi(service: Class<T>, url: String): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(buildOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(service)
        }
    }
}