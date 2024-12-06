package com.keysersoze.techexactlyassignment.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.keysersoze.techexactlyassignment.model.AppModel

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val _appList = MutableLiveData<List<AppModel>>()
    val appList: LiveData<List<AppModel>> get() = _appList

    private var fullAppList = listOf<AppModel>()

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        val pm = getApplication<Application>().packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        fullAppList = apps.map {
            AppModel(
                name = pm.getApplicationLabel(it).toString(),
                packageName = it.packageName,
                icon = pm.getApplicationIcon(it),
                isEnabled = false
            )
        }.sortedBy { it.name }
        _appList.value = fullAppList
    }

    fun filterApps(query: String) {
        _appList.value = if (query.isEmpty()) {
            fullAppList
        } else {
            fullAppList.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun updateAppToggle(app: AppModel, isEnabled: Boolean) {
        fullAppList.find { it.packageName == app.packageName }?.isEnabled = isEnabled
    }
}