package com.wordpress.anujsaxenadev.common

/**
 * Wrapper function to `lazy` for thread safe creation of variables.
 *
 * @param initializer defines how the variable be created.
 *
 * @return object of type [T] lazily
 */
fun <T> lazySync(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.SYNCHRONIZED, initializer)
}