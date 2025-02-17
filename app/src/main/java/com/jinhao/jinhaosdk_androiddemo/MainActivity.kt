package com.jinhao.jinhaosdk_androiddemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessory
import com.jinhao.jinhaosdk.shared.accessory.Accessory
import com.jinhao.jinhaosdk.shared.accessory.AccessoryManager
import com.jinhao.jinhaosdk.shared.accessory.AccessoryManagerScanningListener
import com.jinhao.jinhaosdk.shared.utils.BluetoothPermissionHelper
import com.jinhao.jinhaosdk_androiddemo.ui.theme.JinHaoSDKAndroidDemoTheme
class MainActivity : ComponentActivity(), AccessoryManagerScanningListener {

    private var accessorys = mutableStateListOf<JinHaoAccessory>()

    private var loadingState = mutableStateOf<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataHolder.accessoryManager = AccessoryManager(this);
        DataHolder.accessoryManager?.setScanningListener(this);

        setContent {
            MyApp()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === BluetoothPermissionHelper.REQUEST_CODE_BLUETOOTH) {
            val permissionsGranted = BluetoothPermissionHelper.handlePermissionsResult(
                requestCode, permissions, grantResults
            )
            if (permissionsGranted) {
                // Permissions granted, Bluetooth operations can now begin.
                Toast.makeText(this, "Bluetooth permissions is ok", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Permissions not granted, please prompt the user.
                Toast.makeText(this, "Bluetooth permissions are required", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun accessoryManagerIsScanning(manager: AccessoryManager?, isScanning: Boolean) {
        loadingState.value = isScanning;
    }

    override fun accessoryManagerDidDiscover(
        manager: AccessoryManager?,
        device: Accessory?,
        rssi: Int?
    ) {
        if (device is JinHaoAccessory) {
            Log.w("tag", String.format("name=${device.deviceName}, address=${device.address}"))
            accessorys.add(device)
        }
    }

    override fun accessoryManagerDidUpdate(
        manager: AccessoryManager?,
        device: Accessory?,
        rssi: Int?
    ) {

    }

    /**
     * UI
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyApp() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Demo") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF6200EE), // 背景颜色
                        titleContentColor = Color.White     // 标题文本颜色
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                //检查蓝牙权限
                                if (BluetoothPermissionHelper.checkAndRequestBluetoothPermissions(this@MainActivity)){
                                    accessorys.clear()
                                    DataHolder.accessoryManager?.clearAccessories()
                                    DataHolder.accessoryManager?.startScan(5)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search, // 可以选择其他图标
                                tint = Color.White,
                                contentDescription = "Scan"
                            )
                        }
                    }
                )
            },
            content = {
                // 使用 paddingValues 以避免内容被导航栏覆盖
                ItemList(
                    Modifier
                        .fillMaxSize()
                        .padding(it),
                    accessories = accessorys
                )
                AppWithGlobalLoading(isLoading = loadingState.value) {}
            }
        )
    }
}



@Composable
fun ItemList(modifier: Modifier, accessories: List<JinHaoAccessory>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(accessories) {
            ListItem(item = it)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ListItem(item: JinHaoAccessory) {
    val context = LocalContext.current
    BasicText(
        text = item.deviceName,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                // 创建 Intent 并传递数据
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("name", item.deviceName) // 传递 item 信息
                intent.putExtra("address", item.address) // 传递 item 信息
                context.startActivity(intent)  // 启动新 Activity
            }
    )
}


@Composable
fun AppWithGlobalLoading(isLoading: Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 全局菊花加载进度条
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)), // 半透明背景
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}