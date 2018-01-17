package denis.musicplayer.service.focus

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.support.annotation.RequiresApi
import denis.musicplayer.di.ApplicationContext
import denis.musicplayer.service.music.MusicManager
import javax.inject.Inject

/**
 * Created by denis on 16/01/2018.
 */
class AppAudioFocusManager
    @Inject constructor(@ApplicationContext val context: Context)
    : AudioFocusManager, AudioManager.OnAudioFocusChangeListener  {

    private val TAG = "AudioFocusManager"

    private var audioManager: AudioManager? = null

    private lateinit var musicManager: MusicManager

    override fun onAudioFocusChange(focusState: Int) {
        when(focusState) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                musicManager.resumeTrack()
                musicManager.setVolume(1.0f, 1.0f)
            }
            AudioManager.AUDIOFOCUS_LOSS -> musicManager.pauseTrack()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> musicManager.pauseTrack()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> musicManager.setVolume(0.1f, 0.1f)

        }
    }

    override fun requestAudioFocus() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) requestAudioFocusAndroid8()
        else requestAudioFocusAndroidPre8()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestAudioFocusAndroid8() {
        audioManager?.requestAudioFocus(getAudioFocusRequestAndroid8())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAudioFocusRequestAndroid8(): AudioFocusRequest {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val playbackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        return AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(this)
                .build()
    }

    @Suppress("DEPRECATION")
    override fun abandonAudioFocus() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) audioManager?.abandonAudioFocusRequest(getAudioFocusRequestAndroid8())
        else audioManager?.abandonAudioFocus(this)
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun requestAudioFocusAndroidPre8() {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager?.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    override fun setMusicManager(musicManager: MusicManager) {
        this.musicManager = musicManager
    }
}