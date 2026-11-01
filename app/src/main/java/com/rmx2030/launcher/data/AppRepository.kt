package com.rmx2030.launcher.data

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppInfo(
    val packageName: String,
    val label: String,
    val icon: Drawable
)

class AppRepository(private val context: Context) {
    suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfoList = context.packageManager.queryIntentActivities(intent, 0)
        
        resolveInfoList.map { resolveInfo ->
            val appInfo = resolveInfo.activityInfo.applicationInfo
            AppInfo(
                packageName = appInfo.packageName,
                label = resolveInfo.loadLabel(context.packageManager).toString(),
                icon = resolveInfo.loadIcon(context.packageManager)
            )
        }.sortedBy { it.label.lowercase() }
    }

    fun launchApp(packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }
}
