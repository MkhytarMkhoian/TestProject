package com.test.project.features.links

import com.test.project.di.modules.NetworkModule
import com.test.project.features.links.models.LinksResponse
import com.test.project.features.links.models.LinksResponseJsonAdapter
import com.test.project.shared.extensions.wtf

fun moshi() = NetworkModule.provideMoshi()

fun homePage(): LinksResponse {
  val adapter = LinksResponseJsonAdapter(moshi())
  return adapter.fromJson(homePage) ?: wtf()
}

val homePage: String by lazy {
  """  { "head": {	"title": "Browse",
    "status": "200"}, "body": [
    { "element" : "outline",
      "type" : "link",
      "text" : "Local Radio",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=local",
      "key" : "local" },
    { "element" : "outline",
      "type" : "link",
      "text" : "Music",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=music",
      "key" : "music" },
    { "element" : "outline",
      "type" : "link",
      "text" : "Talk",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=talk",
      "key" : "talk" },
    { "element" : "outline",
      "type" : "link",
      "text" : "Sports",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=sports",
      "key" : "sports" },
    { "element" : "outline",
      "type" : "link",
      "text" : "By Location",
      "URL" : "http://opml.radiotime.com/Browse.ashx?id=r0",
      "key" : "location" },
    { "element" : "outline",
      "type" : "link",
      "text" : "By Language",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=lang",
      "key" : "language" },
    { "element" : "outline",
      "type" : "link",
      "text" : "Podcasts",
      "URL" : "http://opml.radiotime.com/Browse.ashx?c=podcast",
      "key" : "podcast" }
    ] }"""
}