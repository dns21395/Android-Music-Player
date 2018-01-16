package denis.musicplayer.service.music

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews
import denis.musicplayer.R
import denis.musicplayer.data.DataManager
import denis.musicplayer.data.media.model.Track
import denis.musicplayer.di.ApplicationContext
import denis.musicplayer.service.AppMusicService
import denis.musicplayer.ui.main.MainActivity
import denis.musicplayer.utils.BytesUtil
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Singleton
import javax.inject.Inject
import android.media.AudioFocusRequest




/**
 * Created by denis on 10/01/2018.
 */

@Singleton
class AppMusicManager
    @Inject constructor(@ApplicationContext val context: Context,
                        val dataManager: DataManager)
    : MusicManager, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {


    private val TAG = "AppMusicManager"

    companion object {
        val KEY_ACTION = "key_action"
    }

    private var channelId = "music_channel_id"

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var tracks = ArrayList<Track>()
    private var currentTrackPosition = 0
    private var resumePosition = 0

    private val currentTrackBehaviour: BehaviorSubject<Track> = BehaviorSubject.create()
    private val actionIsPlaying: BehaviorSubject<Boolean> = BehaviorSubject.create()

    private var audioManager: AudioManager? = null

    private var musicService: AppMusicService? = null
    override fun setService(service: AppMusicService) {
        this.musicService = service
    }

    init {
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        Log.d(TAG, "init")
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setAudioAttributes(audioAttributes)
        requestAudioFocus()
    }

    override fun getCurrentTrackBehaviour(): BehaviorSubject<Track> = currentTrackBehaviour

    override fun actionBehaviour(): BehaviorSubject<Boolean> = actionIsPlaying

    override fun playTrack() {
        requestAudioFocus()
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(tracks[currentTrackPosition].data)
        mediaPlayer.prepare()
        mediaPlayer.start()
        currentTrackBehaviour.onNext(tracks[currentTrackPosition])
        actionIsPlaying.onNext(true)
        buildNotification()
    }

    override fun resumePause() {
        when(isPlaying()) {
            true -> pauseTrack()
            false -> resumeTrack()
        }
    }

    override fun pauseTrack() {
        resumePosition = mediaPlayer.currentPosition
        mediaPlayer.pause()
        actionIsPlaying.onNext(false)
        buildNotification()
    }

    override fun resumeTrack() {
        requestAudioFocus()
        mediaPlayer.seekTo(resumePosition)
        mediaPlayer.start()
        actionIsPlaying.onNext(true)
        buildNotification()
    }

    override fun updateTracks(tracks: ArrayList<Track>, currentTrackPosition: Int) {
        this.tracks = tracks
        this.currentTrackPosition = currentTrackPosition
    }

    override fun previousTrack() {
        when(currentTrackPosition - 1) {
            -1 -> currentTrackPosition = tracks.size - 1
            else -> currentTrackPosition--
        }
        playTrack()
    }

    override fun nextTrack() {
        when(currentTrackPosition + 1) {
            tracks.size -> currentTrackPosition = 0
            else -> currentTrackPosition++
        }
        playTrack()
    }

    override fun buildNotification() {
        val view = RemoteViews(context.packageName, R.layout.notification_music_player)
        val currentTrack = getCurrentTrack()

        view.setTextViewText(R.id.trackTitle, currentTrack.title)
        view.setTextViewText(R.id.trackArtist, currentTrack.artist)

        val bitmap = BitmapFactory.decodeFile(dataManager.getAlbumImagePath(currentTrack.albumId))

        when(bitmap) {
            null -> view.setImageViewResource(R.id.cover, R.drawable.no_music)
            else -> view.setImageViewBitmap(R.id.cover, bitmap)
        }


        view.setOnClickPendingIntent(R.id.next, handleActions(MusicManagerAction.NEXT))
        view.setOnClickPendingIntent(R.id.previous, handleActions(MusicManagerAction.PREVIOUS))
        view.setOnClickPendingIntent(R.id.close, handleActions(MusicManagerAction.CLOSE))

        when(isPlaying()) {
            true -> view.setImageViewResource(R.id.playPause, R.drawable.pause)
            false -> view.setImageViewResource(R.id.playPause, R.drawable.play)
        }

        view.setOnClickPendingIntent(R.id.playPause, handleActions(MusicManagerAction.RESUMEPAUSE))

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                       Intent.FLAG_ACTIVITY_SINGLE_TOP or
                       Intent.FLAG_ACTIVITY_CLEAR_TASK or
                       Intent.FLAG_ACTIVITY_NEW_TASK


        val pIntent = PendingIntent.getActivity(context, 0, intent, 0)

        channelId = if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) createNotificationChannel() else ""

        val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.music)
                .setContent(view)
                .setContentIntent(pIntent)
                .build()

        musicService?.showNotificationForeground(notification)
    }

    override fun getTracksSize(): Int = tracks.size

    override fun closeMusicPlayer() {
        abandonAudioFocus()
        actionIsPlaying.onNext(false)
        mediaPlayer.stop()
        musicService?.stopService()
    }

    override fun isPlaying(): Boolean = mediaPlayer.isPlaying

    private fun handleActions(action: MusicManagerAction): PendingIntent? {
        val intent = Intent(context, AppMusicService::class.java)
        val bundle = BytesUtil.toByteArray(action)
        intent.putExtra(KEY_ACTION, bundle)
        return PendingIntent.getService(context, action.value, intent, 0)
    }

    override fun getCurrentTrack(): Track = tracks[currentTrackPosition]

    override fun onCompletion(p0: MediaPlayer?) {
        nextTrack()
    }

    override fun onAudioFocusChange(focusState: Int) {
        Log.d(TAG, "FOCUS STATE : $focusState")
        when(focusState) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                Log.d(TAG, "AUDIOFOCUS_GAIN")
                resumeTrack()
                mediaPlayer.setVolume(1.0f, 1.0f)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                Log.d(TAG, "AUDIOFOCUS_LOSS")
                pauseTrack()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                Log.d(TAG, "LOSS_TRANSIENT")
                pauseTrack()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                Log.d(TAG, "LOSS_TRANSIENT_CAN_DUCK")
                mediaPlayer.setVolume(0.1f, 0.1f)
            }
        }
    }

    private fun requestAudioFocus() {
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
    private fun abandonAudioFocus() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) audioManager?.abandonAudioFocusRequest(getAudioFocusRequestAndroid8())
        else audioManager?.abandonAudioFocus(this)
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun requestAudioFocusAndroidPre8() {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager?.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "android_music_service"
        val channelName = "Android Music Service"
        val chan = NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        chan.importance = NotificationManager.IMPORTANCE_NONE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


    override fun makeAction(action: MusicManagerAction) {
        if(tracks.size > 0) {
            when (action) {
                MusicManagerAction.PLAY -> playTrack()
                MusicManagerAction.RESUMEPAUSE -> resumePause()
                MusicManagerAction.PREVIOUS -> previousTrack()
                MusicManagerAction.NEXT -> nextTrack()
                else -> closeMusicPlayer()
            }
        }
    }
}

enum class MusicManagerAction(val value: Int) {
    PLAY(0),
    RESUMEPAUSE(1),
    PREVIOUS(2),
    NEXT(3),
    CLOSE(4)
}