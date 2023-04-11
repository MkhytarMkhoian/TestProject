package com.test.project.shared.extensions

suspend fun exceptionsHandler(
  execute: suspend () -> Unit,
  exceptionsHandler: (suspend (Throwable) -> Unit)? = null,
  finally: (suspend () -> Unit)? = null,
) {
  try {
    execute()
  } catch (e: Throwable) {
    if (exceptionsHandler == null) throw e
    exceptionsHandler(e)
  } finally {
    finally?.invoke()
  }
}

class DeveloperException(developerMessage: String, cause: Throwable? = null) :
  Exception("Developer run into mistake: $developerMessage", cause)


fun wtf(
  msg: String = "Something went wrong and this is pretty confusing for a developer.",
  cause: Throwable? = null
): Nothing = throw DeveloperException(msg, cause)
