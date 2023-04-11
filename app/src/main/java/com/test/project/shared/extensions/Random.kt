package com.test.project.shared.extensions

import java.util.UUID

fun generateId(): Long {
  return UUID.randomUUID().toString().hashCode().toLong()
}