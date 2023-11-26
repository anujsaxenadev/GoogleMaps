package com.wordpress.anujsaxenadev.googlemaps.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * An Extension work similar to `launchWhenStarted` to launch a coroutine if lifecycle is stated.
 *
 * @param coroutineScope [CoroutineScope] on which the `block` will run
 * @param block code to run after lifecycle is started
 */

fun Fragment.launchWhenStarted(coroutineScope: CoroutineScope, block: suspend () -> Unit) {
    coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}