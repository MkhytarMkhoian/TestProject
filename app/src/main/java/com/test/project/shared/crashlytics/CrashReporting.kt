package com.test.project.shared.crashlytics

import java.io.PrintStream
import java.io.PrintWriter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashReporting
@Inject constructor(

) {

  companion object {
    const val TAG_NETWORK = "network_tag | see log -> |"
  }

  fun log(message: String) {
  }

  fun log(error: Throwable, tag: String = "", message: String = "") {
  }

  class CrashlyticsErrorWrapper(message: String, cause: Throwable) :
    Throwable(message = message, cause = cause) {
    override fun getStackTrace(): Array<StackTraceElement> = cause.stackTrace

    override fun printStackTrace() = cause.printStackTrace()

    override fun printStackTrace(s: PrintStream) = cause.printStackTrace(s)

    override fun printStackTrace(s: PrintWriter) = cause.printStackTrace(s)

    override val cause: Throwable = super.cause!!
  }
}
