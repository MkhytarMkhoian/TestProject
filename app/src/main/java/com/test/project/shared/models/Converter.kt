package com.test.project.shared.models

interface Converter <In, Out> {

  fun convertInput(input: In): Out

}
