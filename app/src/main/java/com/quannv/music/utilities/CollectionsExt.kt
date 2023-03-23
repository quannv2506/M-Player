package com.quannv.music.utilities

fun <T> List<T>?.convertToMutableList(): MutableList<T> {
    return if (this.isNullOrEmpty())
        mutableListOf()
    else this.toMutableList()
}

/**
 * Adds the item if it is not in the collection or removes it if it is.
 * @return Whether this collection contains the item, AFTER this operation
 */
fun <T> MutableCollection<T>.addOrRemove(item: T): Boolean {
    return if (contains(item)) {
        remove(item)
        false
    } else {
        add(item)
        true
    }
}

/**
 * Replaces the first item within the list that matches the given predicate or adds the item to
 * the list if none match.
 */
fun <T> MutableList<T>.addOrReplace(item: T, predicate: (T) -> Boolean): Boolean {
    return this.indexOfFirst { predicate.invoke(it) }
        .takeIf { it >= 0 }
        ?.let { this[it] = item }
        ?.let { true }
        ?: this.add(item)
            .let { false }
}