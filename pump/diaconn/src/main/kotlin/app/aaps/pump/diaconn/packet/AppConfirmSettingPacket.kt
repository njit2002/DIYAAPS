package app.aaps.pump.diaconn.packet

import app.aaps.core.interfaces.logging.LTag
import dagger.android.HasAndroidInjector
import app.aaps.pump.diaconn.DiaconnG8Pump
import javax.inject.Inject

/**
 * AppConfirmSettingPacket
 */
@Suppress("SpellCheckingInspection")
class AppConfirmSettingPacket(
    injector: HasAndroidInjector,
    private var reqMsgType: Byte, // 명령코드
    private var otp: Int // 응답시 전달받은 opt (random 6 digit number)
) : DiaconnG8Packet(injector) {

    @Inject lateinit var diaconnG8Pump: DiaconnG8Pump

    init {
        msgType = 0x37
        aapsLogger.debug(LTag.PUMPCOMM, "AppConfirmSettingPacket init")
    }

    override fun encode(msgSeq: Int): ByteArray {
        val buffer = prefixEncode(msgType, msgSeq, MSG_CON_END)

        buffer.put(reqMsgType)  // 명령코드
        buffer.putInt(otp) //  응답시 전달받은 opt (random 6digit numbner)

        aapsLogger.debug(LTag.PUMPCOMM, "reqMsgType -> $reqMsgType")
        aapsLogger.debug(LTag.PUMPCOMM, "otp -> $otp")

        return suffixEncode(buffer)
    }

    override val friendlyName = "PUMP_APP_CONFRIM_SETTING"
}