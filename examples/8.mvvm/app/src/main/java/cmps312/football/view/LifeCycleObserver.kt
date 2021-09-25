package cmps312.football.view

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifeCycleObserver(
    private val lifecycle: Lifecycle,
    private val tag: String,
    private val language: String,
    private val screenOrientation: String
) : LifecycleObserver {
    private val TAG = "LifeCycle->$tag"

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        val msg = "onCreate. Language = $language - Orientation = $screenOrientation - ${lifecycle.currentState}"
        Log.d(TAG, msg)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "onStart - ${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "\uD83E\uDD29\uD83E\uDD29 onResume \uD83E\uDD29\uD83E\uDD29 - ${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(TAG, "☠☠ onDestroy ☠☠ - ${lifecycle.currentState}")
    }

    init {
        lifecycle.addObserver(this)
    }
}