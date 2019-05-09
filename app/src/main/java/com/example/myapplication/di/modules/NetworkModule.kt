package com.example.myapplication.di.modules

import android.app.Application
import android.util.Base64
import com.example.myapplication.api.ApiService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

const val TOKEN = "twp_ocsj8PR64FIV48fHVXCy75gBruca"

@Module
class NetworkModule(private val url: String) {
    // url https://yat.teamworkpm.net
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun providesOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun providesRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)

    @Provides
    fun providesApi(
        retrofit: Retrofit.Builder,
        url: String
    ): ApiService =
        retrofit.baseUrl(url)
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            var request = it.request()
            val headers = request.headers().newBuilder().add("Authorization", getAuthorizationHeader()).build()
            request = request.newBuilder().headers(headers).build()
            it.proceed(request)
        }
    }

    @Provides
    fun providesUrl(): String = url


    private fun getAuthorizationHeader(): String {
        val credential = "$TOKEN:XX"
        return "Basic " + Base64.encodeToString(credential.toByteArray(), Base64.NO_WRAP)
    }

}