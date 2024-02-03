package pkg.rnuniqueidentifier

import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Callback
import android.util.Log
import android.media.MediaDrm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.*

class RnUniqueIdentifierModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun getPersistentIdentifier(callback: Callback): Unit {
    GlobalScope.launch(Dispatchers.IO) {
      val result = generatePersistentIdentifier()
        if (result != null) {
          callback.invoke(result)
        } else {
          callback.invoke(null)
        }
    }
  }

  private fun generatePersistentIdentifier(): String? {
    val widevineUuid = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
      var wvDrm: MediaDrm? = null

      try {
        wvDrm = MediaDrm(widevineUuid)
          val widevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
          val md = MessageDigest.getInstance("SHA-256")
          md.update(widevineId)
          return bytesToHexString(md.digest())
      } catch (e: Exception) {
        return null
      } finally {
        if (wvDrm != null) {
          wvDrm.close();
        }
      }
  }

  private fun bytesToHexString(bytes: ByteArray): String {
    val hexString = StringBuilder()
      for (aByte in bytes) {
        val hex = Integer.toHexString(0xFF and aByte.toInt())
          if (hex.length == 1) {
            hexString.append('0')
          }
        hexString.append(hex)
      }
    return hexString.toString()
  }

  companion object {
    const val NAME = "RnUniqueIdentifier"
  }
}
