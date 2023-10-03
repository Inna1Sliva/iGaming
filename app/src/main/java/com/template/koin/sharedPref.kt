package com.template.koin

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.template.Constant
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPrefModel = module{
    single<SharedPreferences> { providesInitSharedPref(androidApplication()) }
    single <Editor>{ providesEditorSharedPref(get())  }

}
    fun providesInitSharedPref(context: Context):SharedPreferences{
        return context.getSharedPreferences(Constant.APP_RESULT, MODE_PRIVATE)
        }
    fun providesEditorSharedPref(shared:SharedPreferences):Editor{
         return shared.edit()
}