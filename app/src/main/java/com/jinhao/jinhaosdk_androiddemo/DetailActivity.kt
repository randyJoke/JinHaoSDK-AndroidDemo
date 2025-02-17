package com.jinhao.jinhaosdk_androiddemo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessory
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessoryListener
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoDsp
import com.jinhao.jinhaosdk.aid.jinhao.service.JinHaoRequest
import com.jinhao.jinhaosdk.shared.accessory.Accessory
import com.jinhao.jinhaosdk.shared.accessory.AccessoryService
import java.util.function.Consumer
import kotlin.math.roundToInt

class DetailActivity : ComponentActivity(), JinHaoAccessoryListener {

    private var tag = DetailActivity::class.simpleName

    private var device: JinHaoAccessory? = null

    private var connectedState = mutableStateOf<Boolean>(false)

    private var volumeState = mutableStateOf<Float>(0F)

    private var programState = mutableStateOf<Float>(0F)

    private var noiseState = mutableStateOf<Float>(-1F)

    private var mpoState = mutableStateOf<Float>(-1F)

    private var directionState = mutableStateOf<Float>(-1F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getStringExtra("address") // 获取传递的数据
        device = DataHolder.accessoryManager?.deviceForDeviceId(itemId) as JinHaoAccessory
        configureUI()
    }

    override fun onStop() {
        super.onStop()
        if (device?.isConnected == true) {
            device?.disconnect(true)
        }
    }

    private fun configureUI() {
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp), // 子项间距
                horizontalAlignment = Alignment.CenterHorizontally // 水平对齐方式
            ) {
                Text(text = "${device?.deviceName}")
                Button(
                    enabled = !connectedState.value,
                    onClick = {
                        val connect = device?.connect(this@DetailActivity)
                    }) {
                    Text("connect")
                }
                Button(
                    enabled = connectedState.value,
                    onClick = {
                        val connect = device?.disconnect(false)
                    }) {
                    Text("disconnect")
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Volume")
                    Slider(value = this@DetailActivity.volumeState.value,
                        onValueChange = {
                            volumeState.value = it
                            device?.excute(JinHaoRequest.controlVolume(it.roundToInt(), programState.value.roundToInt()), Consumer {
                                
                            })},
                        valueRange = 0f..10f,
                        steps = 9
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Mode")
                    Slider(value = this@DetailActivity.programState.value,
                        onValueChange = { program ->
                            programState.value = program
                            device?.excute(JinHaoRequest.controlProgram(program.roundToInt()), Consumer { result ->
                                if (result.isSuccess && device != null) {

                                }
                            })},
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Noise")
                    Slider(value = this@DetailActivity.noiseState.value,
                        onValueChange = {
                            noiseState.value = it
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.OFF
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.WEAK
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.MEDIUM
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.STRONG
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "MPO")
                    Slider(value = this@DetailActivity.mpoState.value,
                        onValueChange = {
                            mpoState.value = it
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.OFF
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.LOW
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.MEDIUM
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.HIGH
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Direction")
                    Slider(value = this@DetailActivity.directionState.value,
                        onValueChange = {
                            directionState.value = it
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.NORMAL
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.TV
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.METTING
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.FACE
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }
            }
        }
    }

    @Composable
    fun GlobalLoading(isLoading: Boolean, content: @Composable () -> Unit) {
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

    /**
     *  *********************************** JinHaoAccessoryListener ********************************
     */
    override fun deviceDidUpdate(device: Accessory?, state: Int) {

    }

    //当助听器连接成功
    override fun deviceDidConnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = true
            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show()
        }
    }

    //当助听器断开连接
    override fun deviceDidDisconnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    //当助听器连接失败
    override fun deviceDidFailToConnect(device: Accessory?, errorCode: Int) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun deviceDidDiscoverServices(device: Accessory?, services: MutableList<AccessoryService>?) {
        if (device is JinHaoAccessory) {
            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "读取模式失败")
                } else {
                    Log.w(tag, "读取模式成功")
                }
            })
            device.excute(JinHaoRequest.readDsp(0), Consumer {
                Log.w(tag, "读取模式1文件完成")
            })
            device.excute(JinHaoRequest.readDsp(1), Consumer {
                Log.w(tag, "读取模式2文件完成")
            })
            device.excute(JinHaoRequest.readDsp(2), Consumer {
                Log.w(tag, "读取模式3文件完成")
            })
            device.excute(JinHaoRequest.readDsp(3), Consumer {
                Log.w(tag, "读取模式4文件完成")
            })
        }
    }

    override fun deviceBatteryChanged(device: JinHaoAccessory?, bat: Int) {

    }

    override fun deviceDidUpdateValue(device: JinHaoAccessory?) {
        runOnUiThread {
            if (device is JinHaoAccessory) {
                device.dsp?.let {
                    when {
                        it.noise == JinHaoDsp.NOISE.OFF -> {
                            noiseState.value = 0F
                        }
                        it.noise == JinHaoDsp.NOISE.WEAK -> {
                            noiseState.value = 1F
                        }
                        it.noise == JinHaoDsp.NOISE.MEDIUM -> {
                            noiseState.value = 2F
                        }
                        it.noise == JinHaoDsp.NOISE.STRONG -> {
                            noiseState.value = 3F
                        }
                    }
                    when {
                        it.direction == JinHaoDsp.DIRECTION.NORMAL -> {
                            directionState.value = 0F
                        }
                        it.direction == JinHaoDsp.DIRECTION.TV -> {
                            directionState.value = 1F
                        }
                        it.direction == JinHaoDsp.DIRECTION.METTING -> {
                            directionState.value = 2F
                        }
                        it.direction == JinHaoDsp.DIRECTION.FACE -> {
                            directionState.value = 3F
                        }
                    }

                    when {
                        it.mpo == JinHaoDsp.MPO.OFF -> {
                            mpoState.value = 0F
                        }
                        it.mpo == JinHaoDsp.MPO.LOW -> {
                            mpoState.value = 1F
                        }
                        it.mpo == JinHaoDsp.MPO.MEDIUM -> {
                            mpoState.value = 2F
                        }
                        it.mpo == JinHaoDsp.MPO.HIGH -> {
                            mpoState.value = 3F
                        }
                    }
                }
                device.let {
                    volumeState.value = it.volume.toFloat()
                }
                device.let {
                    programState.value = it.program.toFloat()
                }
            }
        }
    }

    //助听器上的调节模式
    override fun didChangedProgramByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
        programState.value = current.toFloat()
        device?.excute(JinHaoRequest.readDsp(current), null)
    }

    //助听器上的调节音量
    override fun didChangedVolumeByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
        volumeState.value = current.toFloat()
    }

}


